import React from 'react'
import './tools.css'
import AppHeader from "../app-header";

export default class Tools extends React.Component {

    constructor (props) {
        super(props);
        this.state = {
            tools: [
                {id:1, name:'saw', brand: 'bosch', year: '2001', address:'dacha', description:'dacha'},
                {id:2, name:'screwdriver',brand: 'makita', year: '2001', address:'dacha', description:'dacha'},
                {id:3, name:'welding machine', brand: 'bosch', year: '2001', address:'dacha', description:'dacha'}
            ]
        }
    } 

    test = () => {
        console.log("row deleted");
    }

    render() {
        const { tools } = this.state;

        return (
        
            <div className='Container'>
                <AppHeader/>
                <h1 className='h2'>Tools component</h1>
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Name</th>
                            <th scope="col">Brand</th>
                            <th scope="col">Year</th>
                            <th scope="col">Address</th>
                            <th scope="col">Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tools.map((e) =>
                            <tr key={e.id}>
                                <td>{e.id}</td>
                                <td>{e.name}</td>
                                <td>{e.brand}</td>
                                <td>{e.year}</td>
                                <td>{e.address}</td>
                                <td>{e.description}</td>
                                <td><button type="button" className="btn btn btn-warning"></button></td>
                                <td><a href="bbb" onClick={this.test}>
                                    <i className="fa fa-trash" aria-hidden="true"></i></a>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        )
    };

}