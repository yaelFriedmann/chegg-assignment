# Chegg-Assignment

## Project description: 
Read .dat file with URLs, scrape and parse the content to get list of questions from each one.
 
## Getting Started
### Prerequisites
Install Docker in your machine, See https://docs.docker.com/installation/#installation for details on setting Docker up for your machine.

### Installation
Download the folder chegg-installer that is attached in the email and unzip it

### Run the docker
cd <path of the chegg-installer folder> and run:
  1. docker build -t chegg-questions:latest .
  2. docker run -d  -p 8080:8080 chegg-questions:latest
## Usage
1. Go to your browser and redirect to http://localhost:8080
2. In the manifest field, insert the URL where your .dat file with your URLs exist
3. If you want specific file types, select in the checkboxes
4. Click submit
5. The question list will be presented on the screen

### About the project
1. The request to retrieve the questions has timeout, you can change the timeout in the properties file (src\main\resources\application.properties)
2. The project was written in java spring boot application
3. All the requests are processed multithread
4. If csv line fields don’t contain 3 fields in the same order (id, text, type), this question won't be retrieve

### Logging
If there is any error, the "error" page will be open,
TODO – add logger

### Testing
TODO – add tests
