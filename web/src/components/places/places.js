import React from 'react';

export default class Places extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            places: [
                {id:1, name:'house', description:'dacha', address:'dacha'},
                {id:2, name:'house', description:'dacha', address:'dacha'},
                {id:3, name:'house', description:'dacha', address:'dacha'},
            ]
        }
    }    
    render() {
        const { places } = this.state;

        return (
            <div className='container'>
                <h1>Places component</h1>
                <table className="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        {places.map((e) =>
                            <tr key={e.id}>
                                <td>{e.id}</td>
                                <td>{e.name}</td>
                                <td>{e.description}</td>
                                <td>{e.address}</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        );
    };
}