import React from 'react'
import AppHeader from "../app-header";


export default class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            books: [
                {
                    id: 1,
                    title: 'Book1',
                    author: 'Author A',
                    genre: 'genre A',
                    publisher: 'publisher A',
                    year: 'year A',
                    name: 'name A',
                    series: 'series A',
                    description: 'description A'
                },
                {
                    id: 2,
                    title: 'Book2',
                    author: 'Author B',
                    genre: 'genre B',
                    publisher: 'publisher B',
                    year: 'year B',
                    name: 'nameB',
                    series: 'series B',
                    description: 'description B'
                },
                {
                    id: 3,
                    title: 'Book3',
                    author: 'Author C',
                    genre: 'genre C',
                    publisher: 'publisher C',
                    year: 'year C',
                    name: 'nameC',
                    series: 'series C',
                    description: 'description C'
                },
                {
                    id: 4,
                    title: 'Book4',
                    author: 'Author D',
                    genre: 'genre D',
                    publisher: 'publisher D',
                    year: 'year D',
                    name: 'nameD',
                    series: 'series D',
                    description: 'description D'
                },
                {
                    id: 5,
                    title: 'Book5',
                    author: 'Author E',
                    genre: 'genre E',
                    publisher: 'publisher E',
                    year: 'year E',
                    name: 'nameE',
                    series: 'series E',
                    description: 'description E'
                },
                {
                    id: 6,
                    title: 'Book6',
                    author: 'Author F',
                    genre: 'genre F',
                    publisher: 'publisher F',
                    year: 'year F',
                    name: 'nameF',
                    series: 'series F',
                    description: 'description F'
                }
            ]
        }
    }

    render() {
        const {books, setBooks} = this.state;

        return (
            <div className='Container'>
                <AppHeader/>
                <div className="main-container">
                    <h1>Books component</h1>
                    <table className="table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Genre</th>
                            <th>Publisher</th>
                            <th>Year</th>
                            <th>Place</th>
                            <th>Series</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        {books.map((e) =>
                            <tr key={e.id}>
                                <td>{e.id}</td>
                                <td>{e.title}</td>
                                <td>{e.author}</td>
                                <td>{e.genre}</td>
                                <td>{e.publisher}</td>
                                <td>{e.year}</td>
                                <td>{e.name}</td>
                                <td>{e.series}</td>
                                <td>{e.description}</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    };


}