import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {fetchCategories, fetchPdfAuthors, fetchPdfBooks} from "./api/api";

export const PdfBooks = () => {
    const dispatch = useDispatch();
    const pageSize = 15;
    const pageNumber =  useSelector(state => state.pdfBooksReducer.pdfbooks.pageNumber);
    const {pdfauthors, pdfAuthLoading, pdfAuthError} = useSelector(state => state.pdfAuthorsReducer);
    const {categories, catLoading, catError} = useSelector(state => state.categoriesReducer);
    const {pdfbooks, loading, error} = useSelector(state => state.pdfBooksReducer.pdfbooks);
    const [storingBook, setStortingBook] = useState({
        file: null,
        title: null,
        category: null,
        authors: [],
        yearOfRelease: null,
        language: null
    });

    useEffect(() => {
        dispatch(fetchPdfBooks(0, pageSize));
        dispatch(fetchCategories());
        dispatch(fetchPdfAuthors());
    }, [dispatch])

    const handleChange = (e) => {
        if (e !== undefined && e.target !== undefined) {
            const {name, value} = e.target;
            setStortingBook((prev) => ({...prev, [name]: value}));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!storingBook.file || !storingBook.title || !storingBook.author || !storingBook.year) {
            alert("Input all data");
            return;
        }
        const formData = new FormData();
        formData.append("file", storingBook.file);
        formData.append("title", storingBook.title);
        formData.append("category", storingBook.category);
        formData.append("author", storingBook.authors);
        formData.append("yearOfRelease", storingBook.yearOfRelease);
        formData.append("author", storingBook.language);

        /*try {
            const res = await fetch("/api/books", {
                method: "POST",
                body: formData,
            });
            if (res.ok) {
                const newBook = await res.json();
                setBooks(prev => [...prev, newBook]);
                setFile(null);
                setTitle("");
                setAuthor("");
                setYear("");
            } else {
                alert("Ошибка при загрузке книги");
            }
        } catch (err) {
            console.error(err);
            alert("Ошибка при отправке запроса");
        }*/
    };

    console.log("!!! pdfbooks: ", pdfbooks);

    return(
        <main className="main-container">
            <nav className="th-buttons-toolbar" title='Pdf books'>

                <input id="file"  type="file" placeholder="select file" value={storingBook.file} placeholder="file" onChange={(e) => setStortingBook({...storingBook, file: e.target.files[0]}) }/>
                <input id="title" type="text" className="th-main-input" name="book name" value={storingBook.title} placeholder="title" onChange={handleChange}/>

                <select className="th-main-input" name="category" aria-label="Category" value={storingBook.category} placeholder="category"
                        onChange={(e) => {
                            console.log(e.target.value);
                            setStortingBook({...storingBook, category: e.target.value})
                        }}>
                    <option value="" disabled hidden>Category</option>
                    {
                        categories.map((category) =>
                            <option key={category.id} value={category}>{category.name}</option>
                        )
                    }
                </select>

                <select id="authors" multiple={true} className="" name="authors" aria-label="Authors" placeholder="authors"
                        value={storingBook.authors.map(a => a.id)}
                        >
                    <option value="Authors" disabled hidden>Authors</option>
                    {
                        pdfauthors.map((pdfAuthor) =>
                            <option key={pdfAuthor.id} value={pdfAuthor.id}>{pdfAuthor.name}</option>
                        )
                    }
                </select>

            </nav>

            <section className="tableContainer">
                <table className="table">
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>Title</th>
                            <th>Category</th>
                            <th>Authors</th>
                            <th>Year of issue</th>
                            <th>Language</th>
                        </tr>
                    </thead>
                    <tbody>
                        {pdfbooks.length !== 0 ? pdfbooks.map((book) =>
                            <tr key={book.id}>
                                <td>{book.id}</td>
                                <td>{book.title}</td>
                                <td>{book.category?.name}</td>
                                <td>
                                    {book.authors?
                                        [...book.authors]
                                            .filter(author => author.name)
                                            .sort((a, b) => a.name.localeCompare(b.name))
                                            .map(author => author.name)
                                            .join(", ")
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
                                    <button className="table-action-btn delete-btn" title="Удалить" onClick={() =>
                                    {
                                        console.log("Current book", book);
                                        //handleDelBook(book);
                                    }
                                    }>🗑️</button>
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