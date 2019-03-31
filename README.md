# Markov-Chain-Generator
Markov Chain Generator (built using React for UI then SpringBoot for the back-end)

![markov](https://user-images.githubusercontent.com/39042426/55288101-71f21580-53fe-11e9-8c51-43fed8ebdb7e.png)

This is an implementation of Markov Chain Generator. 
The input fields are: 

    Dictionary File : The input file that will be used as reference for the Markov Chain Logic
    Prefix Length : The length of the prefix 
    Suffix Length : The length of the suffix 
    Output Size : Sets as a marker for the cut-off of the generated text



## Getting Started

#### UI
The UI is built using React. 
To run this on your machine, here are the steps: 
  ###### Create a React App, then run the following commands to add the necessary custom libraries:

    npm install bootstrap --save
    npm install axios --save
    npm install @material-ui/core --save
    npm install --save reactstrap react react-dom
    
  ###### Copy the src folder in UI repository here then have it replace the src folder on the newly created React App.
   
    
    
--- Assumption/s: 

    REST API for the resource = "http://localhost:8080/v1/markov/output"    




#### Backend
The Backend is a SpringBoot project that is built with Maven. 


--- Assumption/s: 

    Origins = "http://localhost:3000"
    
    
