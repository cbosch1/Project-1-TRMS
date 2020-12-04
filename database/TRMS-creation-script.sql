CREATE TYPE auth_priv AS ENUM ('EMPLOYEE', 'SUPERVISOR', 'DEPT_HEAD', 'BENCO', 'ADMIN');
CREATE TYPE app_stage AS ENUM ('UPLOAD', 'SUPERVISOR', 'DEPT_HEAD', 'BENCO', 'EVENT', 'END');
CREATE TYPE app_status AS ENUM ('PENDING', 'APPROVED', 'DENIED', 'CANCELLED');
CREATE TYPE event_type AS ENUM ('UNI_COURSE', 'SEMINAR', 'CERT_PREP_CLASS', 'CERTIFICATION','TECHNICAL_TRAINING', 'OTHER');

CREATE TABLE employee
(
	emp_id serial primary key,
	lastname varchar(25) NOT NULL,
	firstname varchar(25) NOT NULL,
	title varchar(50),
	supervisor integer NOT NULL,
	department varchar(50) NOT NUll,
	dept_head boolean NOT NULL
);

CREATE TABLE userbase
(
	user_id serial PRIMARY KEY,
	emp_id integer NOT NULL,
	username varchar(50) NOT NULL,
	passphrase varchar(200) NOT NULL,
	privilege auth_priv NOT NULL
);

CREATE TABLE benco_processing
(
	emp_id integer NOT NULL,
	request_id integer NOT NULL,
	PRIMARY KEY(emp_id, request_id)
);

CREATE TABLE info_request
(
	info_id serial PRIMARY KEY,
	related_id integer NOT NULL,
	destination_id integer NOT NULL,
	sender_id integer NOT NULL,
	sender varchar(100) NOT NULL,
	urgent boolean,
	description text NOT NULL,
	request_date date NOT NULL,
	request_time time NOT NULL
);

CREATE TABLE reimbursement
(
	request_id serial PRIMARY KEY,
	emp_id integer NOT NULL,
	ev_location varchar(100) NOT NULL,
	ev_cost numeric(10,2) NOT NULL,
	ev_type event_type NOT NULL,
	description text NOT NULL,
	justification text NOT NULL,
	grading_format varchar(100) NOT NULL,
	grade varchar(100)
);

CREATE TABLE reimburse_status
(
	request_id integer PRIMARY KEY,
	projected_award numeric(10, 2),
	urgent boolean NOT NULL,
	status app_status NOT NULL,
	stage app_stage NOT NULL,
	request_date date NOT NULL,
	request_time time NOT NULL
);

CREATE TABLE attachment
(
	attach_id serial PRIMARY KEY,
	request_id integer NOT NULL,
	file_type varchar(150) NOT NULL,
	file bytea
);

ALTER TABLE benco_processing ADD CONSTRAINT FK_employee
FOREIGN KEY (emp_id) REFERENCES employee (emp_id) 
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE benco_processing ADD CONSTRAINT FK_reimbursement
FOREIGN KEY (request_id) REFERENCES reimbursement (request_id) 
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE info_request ADD CONSTRAINT FK_reimbursement
FOREIGN KEY (related_id) REFERENCES reimbursement (request_id) 
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE info_request ADD CONSTRAINT FK_destination
FOREIGN KEY (destination_id) REFERENCES employee (emp_id) 
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE reimburse_status ADD CONSTRAINT FK_reimbursement
FOREIGN KEY (request_id) REFERENCES reimbursement (request_id) 
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE attachment ADD CONSTRAINT FK_reimbursement
FOREIGN KEY (request_id) REFERENCES reimbursement (request_id) 
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE reimbursement ADD CONSTRAINT FK_employee
FOREIGN KEY (emp_id) REFERENCES employee (emp_id) 
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE userbase ADD CONSTRAINT FK_employee
FOREIGN KEY (emp_id) REFERENCES employee (emp_id) 
ON DELETE CASCADE ON UPDATE CASCADE;
												
-- Procedure used by JDBC to insert into the reimbursement and reimburse_status list properly
CREATE or REPLACE FUNCTION insert_reimbursement(employee_id int4, ev_location varchar(100), ev_cost double precision, ev_type varchar(10), 
												description text, justification text, grading varchar(100), projected_award double precision, urgent boolean,
												status varchar(10), stage varchar(10), request_date varchar(15), request_time varchar(15))
	RETURNS integer
	LANGUAGE plpgsql 
	AS $$
	DECLARE
		reimburseId integer;
		directDept boolean;
	BEGIN 
		
		SELECT e.dept_head INTO directDept FROM employee e WHERE e.emp_id = (SELECT e2.supervisor FROM employee e2 WHERE e2.emp_id = employee_id);

		IF directDept THEN
			INSERT INTO reimbursement VALUES (default, employee_id, ev_location, ev_cost, ev_type::event_type, description, justification, grading) returning request_id into reimburseId;
			INSERT INTO reimburse_status VALUES (reimburseId, projected_award, urgent, status::app_status, 'DEPT_HEAD', request_date::date, request_time::time);		
		
		ELSE
			INSERT INTO reimbursement VALUES (default, employee_id, ev_location, ev_cost, ev_type::event_type, description, justification, grading) returning request_id into reimburseId;
			INSERT INTO reimburse_status VALUES (reimburseId, projected_award, urgent, status::app_status, stage::app_stage, request_date::date, request_time::time);
		
		END IF;
		RETURN reimburseId;
	
	END;$$

CREATE OR REPLACE FUNCTION insert_info_for(req_id integer, sender_id integer, sender varchar(100), 
											urgent boolean, description text, request_date date, request_time time, destination auth_priv)
											
	RETURNS integer
	LANGUAGE plpgsql 
	AS $$
	DECLARE
		reimburseId integer;
		dest_id integer;
	BEGIN 
		
		IF (destination = 'EMPLOYEE') THEN
			SELECT r.emp_id INTO dest_id FROM reimbursement r WHERE r.request_id = req_id;
		
		ELSIF (destination = 'SUPERVISOR') THEN
			SELECT e.supervisor INTO dest_id FROM employee e WHERE e.emp_id = (SELECT r2.emp_id FROM reimbursement r2 WHERE r2.request_id = req_id);
		
		ELSIF (destination = 'DEPT_HEAD') THEN
			SELECT e2.emp_id INTO dest_id FROM employee e2 WHERE e2.department = 
				(SELECT department FROM employee e3 WHERE e3.emp_id = 
					(SELECT emp_id FROM reimbursement r3 WHERE r3.request_id = req_id)) AND e2.dept_head = true;
		
		ELSIF (destination = 'BENCO') THEN
			SELECT bp.emp_id INTO dest_id FROM benco_processing bp WHERE bp.request_id = req_id;
		ELSE 
			dest_id := -1;
		END IF;
	
		INSERT INTO info_request VALUES (default, req_id, dest_id, sender_id, sender, urgent, description, request_date, request_time) returning info_id into reimburseId;
	
		RETURN reimburseId;
	
	SELECT * FROM info_request;
	END;$$
	
CREATE OR REPLACE FUNCTION P_assign_benco() 

	RETURNS trigger
	LANGUAGE plpgsql
	AS $$
	DECLARE
		availaBen employee%rowtype;
		mostAvail integer;
		availCount integer;
		runnerUp integer;
		runCount integer;
		isFirst boolean = true;
	BEGIN
		FOR availaBen IN SELECT emp_id FROM employee WHERE department = 'Benco' AND NOT emp_id IN 
					(SELECT e.emp_id FROM employee e, reimbursement r, reimburse_status rs 
						WHERE e.emp_id = r.emp_id AND r.request_id = rs.request_id AND rs.request_id = NEW.request_id) ORDER BY emp_id
		LOOP
		
			IF isFirst THEN
				mostAvail := availaBen.emp_id;
				SELECT count(*) INTO availCount FROM benco_processing WHERE emp_id = mostAvail;
				isFirst := false;
			END IF;
		
			IF NOT isFirst THEN
				runnerUp := availaBen.emp_id;
				SELECT count(*) INTO runCount FROM benco_processing WHERE emp_id = runnerUp;
				
				IF runCount < availCount THEN
					availCount := runCount;
					mostAvail := runnerUp;
				END IF;
			END IF;
		END LOOP;
		
		INSERT INTO benco_processing VALUES (mostAvail, NEW.request_id);
		RETURN NEW;
	END;$$
	
--DROP Trigger T_assign_benco ON reimburse_status;
CREATE TRIGGER T_assign_benco
	AFTER INSERT ON reimburse_status
	FOR EACH ROW
	EXECUTE PROCEDURE P_assign_benco();

CREATE OR REPLACE FUNCTION P_assign_endstage() 

	RETURNS trigger
	LANGUAGE plpgsql
	AS $$
	DECLARE
		currentStage app_stage;
	BEGIN
		IF NOT new.grade IS NULL THEN
		
			SELECT rs.stage INTO currentStage FROM reimburse_status rs WHERE rs.request_id = new.request_id;
			
			IF currentStage = 'EVENT' THEN
				UPDATE reimburse_status SET stage = 'END'::app_stage WHERE request_id = new.request_id;
			
			ELSE
				-- Do nothing, debugging -> RAISE 'currentStage evaluate to not equal EVENT, returned: %', currentStage;
			END IF;
		
		ELSE
			-- Do nothing, debugging -> RAISE 'new.grade evaluated to null';
		
		END IF;
		RETURN NEW;
	END;$$
	
--DROP Trigger T_assign_endstage ON reimbursement;
CREATE TRIGGER T_assign_endstage
	AFTER UPDATE ON reimbursement
	FOR EACH ROW
	EXECUTE PROCEDURE P_assign_endstage();