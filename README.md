# Project-1-TRMS (Tuition Reimbursement Management System)

## Project Description

The Tuition Reimbursement System, TRMS, allows users to submit reimbursements for courses and training. The submitted reimbursement must be approved by that employee's supervisor, department head, and benefits coordinator. The benefits coordinator then reviews the grade received before finalizing the reimbursement.

## Technologies Used

- Java
- JDBC
- SQL
- Javalin
- HTML
- CSS
- JavaScript
- AJAX

## Features

List of features ready and TODOs for future development
* Tuition Reimbursement submition
* Reimbursement approval for each user type. 
* Additional information requests between users for a given reimbursement request.
* Authentication through JWTs stored as cookies
* Authorization through hashing
* File uploads during multiple stages

To-do list:
* Refactor server side rendering.
* Refactor JWT storage to store as token.

## Getting Started
   
Utilize git bash to clone repository with the command below:
 - git clone https://github.com/YOUR-USERNAME/YOUR-REPOSITORY

Postgres:
  - Please set up a Postgres database utilizing the accompanying script in the database folder.
  - A tutorial can be followed here: <https://www.postgresqltutorial.com/install-postgresql/>

Environment Variables:
  - Application expects the Postgres connection information in the form of three environment variables.
    - TRMS_URL
    - TRMS_USERNAME
    - TRMS_PASSWORD

## License

This project uses the following license:
### GNU GPL v3
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)    
`[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)`
