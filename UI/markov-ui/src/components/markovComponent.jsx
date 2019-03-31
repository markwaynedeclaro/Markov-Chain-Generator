import React, { Component } from 'react';
import { post } from 'axios';
import {
    Container, Row, Col, Form,
    FormGroup, Label, Input,
    Button, Alert
  } from 'reactstrap';

class Markov extends Component {

    constructor(props) {
        super(props);
        this.state = {
            file : null,
            data : '', 
            prefixLength : 1,
            suffixLength : 1,
            outputSize : 1,
            errorMessage : ''
        }
        this.onFormSubmit = this.onFormSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.fileUpload = this.fileUpload.bind(this);
        this.isValidInputFile = this.isValidInputFile.bind(this);
    }
    
    onFormSubmit(e) {
        e.preventDefault(); 

        let { file, prefixLength, suffixLength, outputSize} = this.state;
        console.log(file);
        if (this.isValidInputFile(file)) {
            if (prefixLength.length < 1) { 
                this.setState({ data : '', errorMessage : 'Please input the prefix length' });
            } else if (suffixLength.length < 1) { 
                this.setState({ data : '', errorMessage : 'Please input the suffix length' });
            } else if (outputSize.length < 1) { 
                this.setState({ data : '', errorMessage : 'Please input the output size' });
            } else {
                
                this.fileUpload().then((response)=>{
                  console.log(response.data);
                  if(response.data.success)
                    this.setState({ data : response.data.message, errorMessage : '' });
                  else
                    this.setState({ data : '', errorMessage : response.data.message });      
                });
            }
        }

    }

    isValidInputFile(f) {
        if (f === null) {
            this.setState({ data : '', errorMessage : 'Please choose a file.' });
            return false;
        }
        let parts = f.name.split(".");
        let fileExtension = parts[parts.length - 1];
        if (fileExtension !== 'txt') {      
            this.setState({ data : '', errorMessage : 'File type is not acceptable. Please choose a text file.' });
            return false;
        }
        return true;
    }

    onChange(index, e) {
        if (index === 1)
            this.setState({file:e.target.files[0]});
        else if (index === 2)
            this.setState({prefixLength:e.target.value});      
        else if (index === 3)
            this.setState({suffixLength:e.target.value});      
        else if (index === 4)
            this.setState({outputSize:e.target.value});      
    }

    fileUpload() {
        const url = 'http://localhost:8080/v1/markov/output';
        const formData = new FormData();
        formData.append('file',this.state.file);
        formData.append('prefixLength',this.state.prefixLength);
        formData.append('suffixLength',this.state.suffixLength);
        formData.append('outputSize',this.state.outputSize);

        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        return post(url, formData, config);
    }

    render() {
        return (

            <Container className="App m-5 p-2 w-75 border rounded">

                <h3>Markov Chain Generator</h3>
                <hr/>

                <Row>
                    <Col className="col-3"> 
                        
                        <Container>
                            <Form onSubmit={this.onFormSubmit}>

                                <FormGroup>
                                    <Label><strong>Dictionary File</strong></Label>
                                    <Input type="file" onChange={this.onChange.bind(this, 1)} className="inputFile"/>
                                </FormGroup>
                                <FormGroup>
                                    <Label><strong>Prefix Length</strong></Label>
                                    <Input type="number" name="prefixLength" className="rounded p-l-3 number" min="1" onChange={this.onChange.bind(this, 2)} value={this.state.prefixLength}/>
                                </FormGroup>
                                <FormGroup>
                                    <Label><strong>Suffix Length</strong></Label>
                                    <Input type="number" name="suffixLength" className="rounded p-l-3 number" min="1" onChange={this.onChange.bind(this, 3)} value={this.state.suffixLength}/>
                                </FormGroup>
                                <FormGroup>
                                    <Label><strong>Output Size</strong></Label>
                                    <Input type="number" name="outputSize" className="rounded p-l-3 number" min="1" onChange={this.onChange.bind(this, 4)} value={this.state.outputSize}/>
                                </FormGroup>
                                <br/>
                                <Button className="w-100" color="primary" onClick={this.onSubmit}>Generate</Button>
                                <br/><br/>
                            </Form>
                        </Container>

                    </Col>

                    <Col> 

                        <Input type="textarea" className="w-100 form-control z-depth-1" id="exampleFormControlTextarea6" rows="15" readOnly value={this.state.data}></Input>
                        
                        { this.state.errorMessage.length > 0  &&  
                            <Alert color="danger"> {this.state.errorMessage} </Alert>
                        }

                    </Col>
                </Row>
            
            </Container>

        );
    }
}

export default Markov;