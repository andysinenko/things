import React from 'react'
import AppHeader from "../app-header";
import ThSelect from "../layout/select/th-select";


const INITIAL_STATE = {
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
        title: 'Book45',
        author: 'Author P',
        genre: 'genre N',
        publisher: 'publisher B',
        year: 'year B',
        name: 'nameB',
        series: 'series B',
        description: 'description B'
    },
    {
        id: 3,
        title: 'Book33',
        author: 'Author Q',
        genre: 'genre W',
        publisher: 'publisher C',
        year: 'year C',
        name: 'nameC',
        series: 'series C',
        description: 'description C'
    },
    {
        id: 4,
        title: 'Book4',
        author: 'Author A',
        genre: 'genre GH',
        publisher: 'publisher D',
        year: 'year D',
        name: 'nameD',
        series: 'series D',
        description: 'description D'
    },
    {
        id: 5,
        title: 'Book5',
        author: 'Author A',
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
        author: 'Author L',
        genre: 'genre F',
        publisher: 'publisher F',
        year: 'year F',
        name: 'nameF',
        series: 'series F',
        description: 'description F'
    }
],
    title: '',
    author: '',
    genre: '',
    publisher: '',
    year: '',
    name: '',
    series: '',
    description: '',
    sortMenu: [{id: 1, value: 'id', innerText: 'id', key: 'id'}, {id: 2, value: 'title', innerText: 'Title', key: 'id'}, {id: 3, value: 'author', innerText: 'Author', key: 'id'}, {id: 4, value: 'genre', innerText: 'Genre', key: 'id'}]
};

export default class Books extends React.Component {

    constructor(props) {
        super(props);
        this.state = INITIAL_STATE;
    }

    onSortSelect = (event) => {
        console.log("onSortSelect", event.target.value);
        let booksTemp = this.state.books;
        switch (event.target.value) {
            case "id":
                booksTemp = this.state.books.sort((a, b) => a.id > (b.id));
                console.log(booksTemp);
                this.setState(booksTemp);
                break;
            case "Title":
                booksTemp = this.state.books.sort((a, b) => a.title.localeCompare(b.title));
                console.log(booksTemp);
                this.setState(booksTemp);
                break;
            case "Author":
                booksTemp = this.state.books.sort((a, b) => a.author.localeCompare(b.author));
                console.log(booksTemp);
                this.setState(booksTemp);
                break;
            case "Genre":
                booksTemp = this.state.books.sort((a, b) => a.genre.localeCompare(b.genre));
                this.setState(booksTemp);
                break;
            default:
                break;
        };
    };
    render() {
        return (
            <div className='Container'>
                <AppHeader/>
                <div className="main-container">
                    <h3>Books component</h3>
                    <div className="selector flex-row">
                        <ThSelect onChange={this.onSortSelect} defaultChecked="id" values={this.state.sortMenu}
                                  label="Sort by" label_size={1} input_size={2} required={false}/>
                    </div>
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
                        {this.state.books.length !== 0 ? this.state.books.map((e) =>
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
                        ) : <div><h3>book list is empty</h3></div>
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    };


}