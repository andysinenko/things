import React, {useEffect} from 'react';
import './dashboard.css';
import {useDispatch, useSelector} from "react-redux";
import {getBooksCount} from "../books/reducer/BooksSlice";
import {getPdfBooksCount} from "../pdfbooks/reducer/PdfBooksSlice";


export const Dashboard = () => {
    const booksCount = useSelector(state => state.booksReducer.count);
    const pdfBooksCount = useSelector(state => state.pdfBooksReducer.count);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getBooksCount());
        dispatch(getPdfBooksCount());
    }, [dispatch])

    return (

        <div className="main-container">
            <h3>Dashboard</h3>
            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Entity</th>
                        <th>Count</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>1</th>
                        <td style={{color: "#6f7580"}}>Books registred</td>
                        <td style={{fontWeight: 500}}>{booksCount}</td>
                    </tr>
                    <tr>
                        <th>2</th>
                        <td style={{color: "#6f7580"}}>Pdf books registred</td>
                        <td style={{fontWeight: 500}}>{pdfBooksCount}</td>
                    </tr>
                    </tbody>
                </table>
            </section>
        </div>

    );
}