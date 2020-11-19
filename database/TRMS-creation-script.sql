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
	emp_id integer primary key,
	username varchar(50) NOT NULL,
	passphrase varchar(50) NOT NULL,
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
	justification text NOT NULL
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
	file_type varchar(25) NOT NULL,
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
CREATE or REPLACE FUNCTION insert_reimbursement(emp_id int4, ev_location varchar(100), ev_cost double precision, ev_type varchar(10), 
												description text, justification text, projected_award double precision, urgent boolean,
												status varchar(10), stage varchar(10), request_date varchar(15), request_time varchar(15))
	RETURNS integer
	LANGUAGE plpgsql 
	AS $$
	DECLARE
		reimburseId integer;
	BEGIN 
		
		INSERT INTO reimbursement VALUES (default, emp_id, ev_location, ev_cost, ev_type::event_type, description, justification) returning request_id into reimburseId;
		
		INSERT INTO reimburse_status VALUES (reimburseId, projected_award, urgent, status::app_status, stage::app_stage, request_date::date, request_time::time);
	
		RETURN reimburseId;
	
	END;$$