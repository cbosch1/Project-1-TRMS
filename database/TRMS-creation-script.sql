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
	supervisor integer,
	department varchar(50),
	dept_head boolean
);

CREATE TABLE userbase
(
	emp_id integer primary key,
	username varchar(50) NOT NULL,
	passphrase varchar(50) NOT NULL,
	privilege auth_priv
);

CREATE TABLE benco_processing
(
	emp_id integer,
	request_id integer,
	PRIMARY KEY(emp_id, request_id)
);

CREATE TABLE info_request
(
	info_id serial PRIMARY KEY,
	related_id integer,
	destination_id integer,
	urgent boolean,
	description text,
	request_date date,
	request_time time
);

CREATE TABLE reimbursement
(
	request_id serial PRIMARY KEY,
	emp_id integer,
	ev_location varchar(100),
	ev_cost numeric(10,2),
	ev_type event_type,
	description text,
	justification text
);

CREATE TABLE reimburse_status
(
	request_id integer PRIMARY KEY,
	projected_award numeric(10, 2),
	urgent boolean,
	status app_status,
	stage app_stage,
	request_date date,
	request_time time
);

CREATE TABLE attachment
(
	attach_id serial PRIMARY KEY,
	request_id integer,
	file_type varchar(25),
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