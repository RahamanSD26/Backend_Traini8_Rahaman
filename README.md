# Backend_Traini8_Rahaman
Overview
This project is an MVP for a registry of government-funded training centers. It is built using Spring Boot and provides two APIs for managing training center data, including filtering capabilities.

Features
Create Training Center (POST API)

1-Create and save a new training center with the following fields:
CenterName: (text, required, max 40 characters)
CenterCode: (text, required, exactly 12 alphanumeric characters)
Address: (object, required)
Detailed Address
City
State
Pincode
Student Capacity: (number, optional)
Courses Offered: (List of text, optional)
CreatedOn: (Epoch time, auto-generated)
ContactEmail: (text, optional, validated)
ContactPhone: (text, required, validated)
Validations are performed for mandatory fields, and appropriate error messages are returned on failure.


2-Get Training Centers (GET API)

Retrieve a list of all stored training center information.
Supports filtering based on:
a-ContactPhone
b-ContactEmail
c-GUID
Returns an empty list if no training centers are stored or if no matches are found.

Evaluation Criteria
Functional APIs for creating and retrieving training centers.
Effective data validation using annotations.
Use of MongoDB for database interaction.
Implemented ExceptionHandler for error management.
Well-structured project architecture and code organization.
Consistent coding style and proper naming conventions.
Filtering functionality implemented effectively.

Technologies Used:
Spring Boot
MongoDB (as the database)
Spring Data MongoDB (for data access)
Lombok (for reducing boilerplate code)
Jakarta Validation (for input validation)
Setup Instructions

Prerequisites:
Java 21
Gradle (for dependency management)
MongoDB (local installation or cloud-based service)
IDE (IntelliJ IDEA)

Clone the Repository:
git clone git@github.com:RahamanSD26/Backend_Traini8_Rahaman.git
cd cd Backend_Traini8_Rahaman

Configure MongoDB
Ensure your MongoDB server is running. If you’re using a local instance, make sure to configure the connection settings in application.properties.

Accessing the APIs
Create a Training Center

Endpoint: POST/trainingCenter
Request Body:
{
    "centerName": "TESTCENTER",
    "centerCode": "AYTV124569PL",
    "address":{
        "address": "MUSTAFABAD",
        "city": "New Delhi",
        "state": "Delhi",
        "pinCode":"110094"
    },
    "studentCapacity": 10,
    "courseOffered":["JAVA","PYTHON","JAVASCRIPT"],
    "contactEmail": "raham86tec@gmail.com",
    "contactPhone": "9891538588"
}

Get List of Training Centers

Endpoint: GET /trainingCenter
Query Parameters (optional):
contactPhone: Filter by contact phone number(GET /trainingCenter/phone/9891538588)
contactEmail: Filter by contact email(GET /trainingCenter/email/raham86tec@gmail.com)
guid: Filter by GUID (GET /trainingCenter/TC001)

Exception Handling
Custom exception handling is implemented to provide meaningful error messages for validation failures and other exceptions.

Conclusion
This project serves as a basic implementation of a training center registry, demonstrating the ability to create, validate, retrieve, and filter data effectively using MongoDB. Further enhancements can include improved logging and extended validation rules.
