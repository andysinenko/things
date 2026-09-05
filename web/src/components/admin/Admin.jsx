import { useDispatch, useSelector } from "react-redux";
import React, { useEffect, useState } from "react";
import {fetchAllUsers } from "../signin/reducer/userSlice";
import AdminModal from "./modal/AdminModal";
import {fetchAuthorities} from "./reducer/AuthoritiesSlice.jsx";


export const Admin = () => {
    const dispatch = useDispatch();

    const {users, loading, error} = useSelector(state => state.userReducer);
    const {authorities} = useSelector(state => state.authoritiesReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);

    const [selectedUser, setSelectedUser] = useState({
        id: null,
        username: "",
        password: "",
        email: "",
        firstName: "",
        lastName: "",
        phoneNumber: "",
        authorities: {}
    });

    const [isTreeModalOpen, setIsTreeModalOpen] = useState(false);

    useEffect(() => {
        dispatch(fetchAllUsers());
        dispatch(fetchAuthorities());
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
                <h3>Admin component</h3>
                <p>Error: {error}</p>
            </div>
        </div>);

    const openModal = (modalType) => {
        setModalType(modalType);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setModalType(null);
        setSelectedUser(null);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (modalType === "add") {
                dispatch(addUser(selectedUser));
            } else if (modalType === "delete") {
                dispatch(deleteUser(selectedUser.id));
            } else if (modalType === "edit") {
                dispatch(updateUser({ id: selectedUser.id, tool: selectedUser }));
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const addUser = () => {
        setSelectedUser({
            id: null,
            username: "",
            password: "",
            email: "",
            firstName: "",
            lastName: "",
            phoneNumber: "",
            authorities: {}
        });
        openModal("add");
    };

    const handleEditUser = (user) => {
        setSelectedUser(user);
        openModal("edit");
    }

    const handleDelUser = (user) => {
        setSelectedUser(user);
        openModal("delete", user);
    }

    return (
        <main className="main-container">
            {/* ── Toolbar / Operations with users ── */}
            <nav className="th-buttons-toolbar" aria-label="Tools">
                <button type="button" className="thbtn-add" onClick={addUser}>
                    + Add tool
                </button>
            </nav>

            {/* ── Table ── */}
            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Username</th>
                        <th scope="col">Password</th>
                        <th scope="col">Email</th>
                        <th scope="col">FirstName</th>
                        <th scope="col">LastName</th>
                        <th scope="col">PhoneNumber</th>
                        <th scope="col">Authority</th>
                        <th style={{ textAlign: "center" }}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {Array.isArray(users) && users.length !== 0 ? users
                    .filter(user => typeof user === 'object' && user !== null && 'id' in user)
                    .map((user) =>
                        <tr key={user.id}>
                            <td style={{ color: "#9ca3af" }}>{user.id}</td>
                            <td style={{ fontWeight: 500 }}>{user.username}</td>
                            <td style={{ color: "#6b7280" }}>{user.password}</td>
                            <td>{user.email}</td>
                            <td>{user.firstname}</td>
                            <td>{user.lastName}</td>
                            <td>{user.phonenumber}</td>
                            <td>{user.authority}</td>
                            <td>
                                <div style={{ display: "flex", gap: 4, justifyContent: "center" }}>
                                    <button
                                        className="table-action-btn edit-btn"
                                        title="Edit"
                                        onClick={() => handleEditUser(user)}
                                        aria-label="Edit user"
                                    >
                                        ✎
                                    </button>
                                    <button
                                        className="table-action-btn delete-btn"
                                        title="Delete"
                                        onClick={() => handleDelUser(user)}
                                        aria-label="Delete user"
                                    >
                                        ✕
                                    </button>
                                </div>
                            </td>
                        </tr>
                        ): (
                        <tr>
                            <td colSpan="10" style={{textAlign: "center"}}>
                                <h5>Users list is empty</h5>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </section>

            <AdminModal
                isOpen={isModalOpen}
                onClose={closeModal}
                onSubmit={handleSubmit}
                modalType = {modalType}
                selectedTool={selectedUser}
                setSelectedTool={setSelectedUser}
                isTreeModalOpen={isTreeModalOpen}
                setIsTreeModalOpen={setIsTreeModalOpen}
                authorities={authorities} />
        </main>
    );

}