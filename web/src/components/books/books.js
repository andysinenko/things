import React, {useEffect, useState} from 'react'
import AppHeader from "../app-header";
import ThSelect from "../layout/select/th-select";
import "./books.css";

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faTrash} from '@fortawesome/free-solid-svg-icons';
import {fetchBooksFailure, fetchBooksStart, fetchBooksSuccess} from "./reducer/BooksSlice";
import {useDispatch, useSelector} from "react-redux";
import axios from "axios";


const sortMenu = [{id: 1, value: 'id', innerText: 'id', key: 'id'}, {
    id: 2,
    value: 'title',
    innerText: 'Title',
    key: 'id'
}, {
    id: 3,
    value: 'author',
    innerText: 'Author',
    key: 'id'
}, {id: 4, value: 'genre', innerText: 'Genre', key: 'id'}];

const INITIAL_SORT_MENU_TYPE = {sortType: 'id'};

export const Books = () => {
    //const [books, setBooks] = useState(INITIAL_BOOKS);
    const [sortType, setType] = useState(INITIAL_SORT_MENU_TYPE);
    const dispatch = useDispatch();
    const {books, loading, error} = useSelector(state => state.booksReducer);

    useEffect(() => {
        console.log("useEffect called");
        const fetchPlaces = async () => {
            dispatch(fetchBooksStart());
            try {
                const response = await axios.get("http://localhost:8080/api/v1/books");
                if (response.status === 200) {
                    console.log("Success on fetching books: ", response.status);
                    dispatch(fetchBooksSuccess(response.data));
                } else {
                    console.log("Error on fetching books: ", response.status);
                    dispatch(fetchBooksFailure(`Error: ${response.status}`));
                }
            } catch (error) {
                console.log("Error on fetching books, catch section: ", error.message);
                dispatch(fetchBooksFailure(error.message));
            }
        };

        fetchPlaces();
    }, [dispatch]);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    console.log("BOOKS: ", JSON.stringify(books));
    console.log("BOOKS: ", books.length);

    const onSortSelect = (event) => {
        console.log("onSortSeleced", event.target.value);
        let booksTemp = books;
        switch (event.target.value) {
            case "id":
                booksTemp = books.sort((a, b) => a.id > (b.id));
                console.log("booksTemp: ", booksTemp);
                setType({sortType: 'id'});
                //setBooks(booksTemp);
                dispatch(fetchBooksSuccess(booksTemp));
                break;
            case "Title":
                booksTemp = books.sort((a, b) => a.title.localeCompare(b.title));
                console.log(booksTemp);
                setType({sortType: 'title'});
                //setBooks(booksTemp);
                dispatch(fetchBooksSuccess(booksTemp));
                break;
            case "Author":
                booksTemp = books.sort((a, b) => a.author.localeCompare(b.author));
                console.log(booksTemp);
                setType({sortType: 'author'});
                //setBooks(booksTemp);
                dispatch(fetchBooksSuccess(booksTemp));
                break;
            case "Genre":
                booksTemp = books.sort((a, b) => a.genre.localeCompare(b.genre));
                setType({sortType: 'genre'});
                //setBooks(booksTemp);
                dispatch(fetchBooksSuccess(booksTemp));
                break;
            default:
                break;
        }
        ;
    };

    const handleDelete = (row) => {
        console.log("delete: ", row.id);
    }

    return (
        <div className='Container'>
            <AppHeader/>
            <div className="main-container">
                <h3>Books component</h3>
                <div className="selector flex-row">
                    <ThSelect onChange={onSortSelect} defaultChecked={sortType} values={sortMenu}
                              label="Sort by" label_size={1} input_size={2} required={false}/>
                </div>
                <div className="tableContainer">
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
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        {books.length !== 0 ? books.map((e) =>
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
                                <td>
                                    <button onClick={() => handleDelete(e)}
                                            style={{cursor: 'pointer', border: 'none', background: 'none'}}>
                                        <FontAwesomeIcon icon={faTrash} size="lg" color="red"/>
                                    </button>
                                </td>
                            </tr>
                        ) : <div><h3>book list is empty</h3></div>
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );

}