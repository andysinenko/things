import './Tools.css'
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchTools} from "./api/api";

export const Tools = () => {
    const dispatch = useDispatch();
    const {tools, loading, error} = useSelector(state => state.toolsReducer);

    useEffect(() => {
        fetchTools(dispatch)
    }, [dispatch]);

    if (loading) return (
        <div className='root'>
            <div className="main-container">
                <h3>Tools component</h3>
                <p>Loading...</p>
            </div>
        </div>);
    if (error) return (
        <div className='root'>

            <div className="main-container">
                <h3>Tools component</h3>
                <p>Error: {error}</p>
            </div>
        </div>);

    return (
        <div className='root'>

            <div className="main-container">
                <h3>Tools component</h3>
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
                            <td>
                                <button type="button" className="btn btn btn-warning"></button>
                            </td>
                            <td><a href="bbb" onClick={this.test}>
                                <i className="fa fa-trash" aria-hidden="true"></i></a>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );

}