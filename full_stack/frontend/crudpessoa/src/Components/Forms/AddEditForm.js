import React from 'react';
import { Button, Form, FormGroup, Label, Input } from 'reactstrap';

class AddEditForm extends React.Component {
  state = {
    id: 0,
    nome: '',
    email: '',
    cpf: ''
  }

  onChange = e => {
    this.setState({[e.target.name]: e.target.value})
  }

  submitFormAdd = e => {
    e.preventDefault()
    fetch('http://localhost:8081/infoprice/rest/pessoa', {
      method: 'post',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        nome: this.state.nome,
        email: this.state.email,
        cpf: this.state.cpf
      })
    })
      .then(response => response.json())
      .then(item => {
        if(Array.isArray(item)) {
          this.props.addItemToState(item[0])
          this.props.toggle()
        } else {
          console.log('failure')
        }
      })
      .catch(err => console.log(err))
  }

  submitFormEdit = e => {
    e.preventDefault()
    fetch('http://localhost:8081/infoprice/rest/pessoa', {
      method: 'put',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        nome: this.state.nome,
        email: this.state.email,
        cpf: this.state.cpf
      })
    })
      .then(response => response.json())
      .then(item => {
        if(Array.isArray(item)) {
          // console.log(item[0])
          this.props.updateState(item[0])
          this.props.toggle()
        } else {
          console.log('failure')
        }
      })
      .catch(err => console.log(err))
  }

  componentDidMount(){
    // if item exists, populate the state with proper data
    if(this.props.item){
      const { id, nome, email, cpf } = this.props.item
      this.setState({ id, nome, email, cpf})
    }
  }

  render() {
    return (
      <Form onSubmit={this.props.item ? this.submitFormEdit : this.submitFormAdd}>
        <FormGroup>
          <Label for="first">Nome</Label>
          <Input type="text" name="nome" id="nome" onChange={this.onChange} value={this.state.nome === null ? '' : this.state.nome} />
        </FormGroup>
        <FormGroup>
          <Label for="email">Email</Label>
          <Input type="email" name="email" id="email" onChange={this.onChange} value={this.state.email === null ? '' : this.state.email}  />
        </FormGroup>
        <FormGroup>
          <Label for="phone">Phone</Label>
          <Input type="text" name="cpf" id="cpf" onChange={this.onChange} value={this.state.cpf  === null ? '' : this.state.cpf}  placeholder="ex. 29.103-135" />
        </FormGroup>
        <Button>Salvar</Button>
      </Form>
    );
  }
}

export default AddEditForm