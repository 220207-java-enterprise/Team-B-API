Link to our UI: https://github.com/220207-java-enterprise/Team-B-UI

# Java Enterprise Foundations Project Requirements

## Project Description

For the technology module of your training you are tasked with building a web-based expense reimbursement system. This system will manage the process of reimbursing employees for expenses incurred while on company time. This system will work closely with the internal PRISM application - which is used for processing payments to employees. All registered employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. Finance managers can log in and view all reimbursement requests and past history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement.

### Project Design Specifications and Documents

##### Relational Data Model
![Relational Model](https://github.com/220207-java-enterprise/assignments/blob/main/foundations-project/imgs/ERS%20Relational%20Model.png)

##### Reimbursement Types
Reimbursements are to be one of the following types:
- LODGING 
- TRAVEL 
- FOOD 
- OTHER 

##### System Use Case Diagrams
![System Use Case Diagrams](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20Use%20Case%20Diagram.png)

##### Reimbursment Status State Flow
![Reimbursment Status State Flow](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20State%20Flow%20Diagram.png)

### Technologies

**Persistence Tier**
- PostGreSQL (running locally using Docker)

**Application Tier**
- Java 8
- Spring 5 & Spring Boot
- Apache Maven
- Hibernate & Spring Data
- JSON Web Tokens
- JUnit
- Mockito

**Client Tier**
- HTML
- CSS
- TypeScript
- React

### PRISM

The PRISM application is another internal system that your expense reimbursement will communicate with when employee reimbursement requests are approved. You can find the codebase for the PRISM application with instructions for running it locally [here](https://github.com/220207-java-enterprise/prism).

