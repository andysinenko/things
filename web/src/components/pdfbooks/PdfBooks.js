import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {deletePdfBook, fetchCategories, fetchPdfAuthors, fetchPdfBooks, uploadPdfBook} from "./api/api";


const INITIAL_BOOK = {
    file: null,
    title: '',
    category: '',
    author: {},
    yearOfRelease: '',
    language: ''
};

export const PdfBooks = () => {
    const dispatch = useDispatch();
    const pageSize = 15;
    const pageNumber =  useSelector(state => state.pdfBooksReducer.pdfbooks.pageNumber);
    const {pdfauthors, pdfAuthLoading, pdfAuthError} = useSelector(state => state.pdfAuthorsReducer);
    const {categories, catLoading, catError} = useSelector(state => state.categoriesReducer);
    const {pdfbooks, loading, error} = useSelector(state => state.pdfBooksReducer.pdfbooks);
    const [storingBook, setStoringBook] = useState(INITIAL_BOOK);

    useEffect(() => {
        dispatch(fetchPdfBooks(0, pageSize));
        dispatch(fetchCategories());
        dispatch(fetchPdfAuthors());
    }, [dispatch])

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setStoringBook((prev) => ({...prev, [name]: value}));
        }
    };

    const handleDelBook = (book) => {
        dispatch(deletePdfBook(book.id));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log("!!! storingBook: ", storingBook);
        /*if (!storingBook.file || !storingBook.title || !storingBook.author || !storingBook.year) {
            alert("Input all data");
            return;
        }*/
        const formData = new FormData();
        formData.append("file", storingBook.file);
        formData.append("title", storingBook.title);
        formData.append("category", storingBook.category);
        formData.append("author", storingBook.author);
        formData.append("yearOfRelease", storingBook.yearOfRelease);
        formData.append("language", storingBook.language);

        dispatch(uploadPdfBook(formData));
        setStoringBook(INITIAL_BOOK);

        dispatch(fetchPdfBooks());
    };

    return(
        <main className="main-container">
            <nav className="th-buttons-toolbar" title='Pdf books'>
                <form className="th-buttons-toolbar" onSubmit={handleSubmit}>
                    <input id="file"  type="file" className="th-main-input" placeholder="select file"  onChange={(e) => setStoringBook({...storingBook, file: e.target.files[0]}) }/>
                    <input id="title" type="text" className="th-main-input" name="title" value={storingBook.title} placeholder="title" onChange={handleChange}/>

                    <select id="yearOfRelease" name="yearOfRelease" value={storingBook.yearOfRelease} onChange={handleChange} className="th-main-input">
                        <option value="" disabled hidden>Issue year</option>
                        <option value="2000">2000</option>
                        <option value="2001">2001</option>
                        <option value="2002">2002</option>
                        <option value="2003">2003</option>
                        <option value="2004">2004</option>
                        <option value="2005">2005</option>
                        <option value="2006">2006</option>
                        <option value="2007">2007</option>
                        <option value="2008">2008</option>
                        <option value="2009">2009</option>
                        <option value="2010">2010</option>
                        <option value="2011">2011</option>
                        <option value="2012">2012</option>
                        <option value="2013">2013</option>
                        <option value="2014">2014</option>
                        <option value="2015">2015</option>
                        <option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                        <option value="2019">2019</option>
                        <option value="2020">2020</option>
                        <option value="2021">2021</option>
                        <option value="2022">2022</option>
                        <option value="2023">2023</option>
                        <option value="2024">2024</option>
                        <option value="2025">2025</option>
                    </select>

                    <select
                        className="th-main-input"
                        name="category"
                        aria-label="Category"
                        value={storingBook.category ? storingBook.category.id : ''}
                        onChange={(e) => {
                            const selectedCategory = categories.find(
                                (cat) => cat.id === Number(e.target.value)
                            );
                            console.log("Selected Category ID:", e.target.value);
                            console.log("Selected Category Object:", selectedCategory);
                            setStoringBook({...storingBook, category: Number(e.target.value) });
                        }}
                    >
                        <option value="" disabled hidden>Category</option>
                        {
                            categories.map((category) =>
                                <option key={category.id} value={category.id}>{category.name}</option>
                            )
                        }
                    </select>
                    <select
                        id="authors"
                        multiple={false}
                        className="th-main-input"
                        name="authors"
                        aria-label="Authors"
                        placeholder="authors"
                        value={storingBook.author}
                        onChange={(e) => {
                            const selectedAuthor = pdfauthors.find(
                                (author) => author.id === Number(e.target.value)
                            );
                            setStoringBook({ ...storingBook, author: selectedAuthor });
                        }}>
                        <option value="" disabled hidden>Authors</option>
                        {
                            pdfauthors.map((pdfAuthor) =>
                                <option key={pdfAuthor.id} value={pdfAuthor.id}>{pdfAuthor.name}</option>
                            )
                        }
                    </select>
                    <select id="language" name="language" value={storingBook.language} onChange={handleChange} className="th-main-input">
                        <option value="" disabled hidden>Language</option>
                        <option value="EN">EN</option>
                        <option value="RU">RU</option>
                    </select>
                    <button type="submit" className="th-main-button">Save Changes</button>
                </form>
            </nav>

            <section className="tableContainer">
                <table className="table">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Title</th>
                            <th>Category</th>
                            <th>Author</th>
                            <th>Year of issue</th>
                            <th>Language</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        {pdfbooks.length !== 0 ? pdfbooks.map((book) =>
                            <tr key={book.id}>
                                <td>{book.id}</td>
                                <td>{book.title}</td>
                                <td>{book.category?.name}</td>
                                <td>
                                    {book.author?
                                        [...book.author]
                                            .filter(author => author.name)
                                            .map(author => author.name)
                                        : ''}
                                </td>
                                <td>{book.yearOfRelease}</td>
                                <td>{book.language}</td>
                                <td>
                                    <button className="table-action-btn edit-btn" title="Редактировать" onClick={() => {
                                        console.log("Current book", book);
                                        //handleEditBook(book);
                                    }} >✏️</button>
                                </td>
                                <td>
                                    <button className="table-action-btn delete-btn" title="Удалить" onClick={() => handleDelBook(book)}>🗑️</button>
                                </td>
                            </tr>
                        ):(
                            <tr>
                                <td colSpan="10" style={{textAlign: "center"}}>
                                    <h5>Book list is empty</h5>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </section>
        </main>
    );
};