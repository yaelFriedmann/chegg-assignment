# Chegg-Assignment

## Project description: 
Read .dat file with URLs, and retrieve from each URL the questions in the file

## Getting Started
### Prerequisites
You need to install Docker in your machine

### Installation
1. download the folder chegg-target

### Configuration
1. open cmd on the folder path and run:
  1.1  docker build -t chegg-questions:latest .
  1.2  docker run -d  -p 8080:8080 chegg-questions:latest

## Usage
1. go to your browser and redirect to http://localhost:8080
2. in the manifest field, insert the URL where your .dat file with your URLs exist
3. if you want specific file types, select in the checkboxes
4. click submit
5. the question list will be open in a table

### About the project
1. the request to retrieve the questions has timeout, you can change the timeout in the properties file 
2. the project was written in java spring boot application
3. all the requests are processed multithread
4. If csv line fields don’t contains 3 fields in the same order (id, text, type), this question won't be retrieve

### Logging
If there is any error, the "error" page will be open,
for now, I didn't add logger

### Testing
need to be added...
