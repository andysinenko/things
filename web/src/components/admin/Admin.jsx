import { useDispatch, useSelector } from "react-redux";
import React, { useEffect, useState } from "react";
import {fetchAllUsers, updateUser} from "../signin/reducer/userSlice";
import AdminModal from "./modal/AdminModal";
import {fetchAuthorities} from "./reducer/AuthoritiesSlice.jsx";


export const Admin = () => {
    const dispatch = useDispatch();
    const EMPTY_USER = {
        id: null,
        username: '',
        password: '',
        email: '',
        firstName: '',
        lastName: '',
        phoneNumber: '',
        accountNonExpired: false,
        accountNonLocked: false,
        credentialsNonExpired: false,
        enabled:	false,
        authorities: []
    };
    const {users, loading, error} = useSelector(state => state.userReducer);
    const {authorities} = useSelector(state => state.authoritiesReducer);

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalType, setModalType] = useState(null);

    const [selectedUser, setSelectedUser] = useState(EMPTY_USER);

    const [isTreeModalOpen, setIsTreeModalOpen] = useState(false);

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

    useEffect(() => {
        dispatch(fetchAllUsers());
        dispatch(fetchAuthorities());
        console.log("RAW BODY AFTER do fetchAuthorities:", JSON.stringify(selectedUser));
        console.log("users do fetchAllUsers:", JSON.stringify(users));
    }, [dispatch]);

    const openModal = (modalType) => {
        setModalType(modalType);
        setIsModalOpen(true);
        console.log("RAW BODY AFTER OPEN MODAL:", JSON.stringify(selectedUser));
    };

    const closeModal = () => {
        console.log("RAW BODY BEFORE OPEN MODAL:", JSON.stringify(selectedUser));
        setIsModalOpen(false);
        setModalType(null);
        setSelectedUser(EMPTY_USER);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (modalType === "add") {
                dispatch(addUser(selectedUser));
            } else if (modalType === "delete") {
                dispatch(deleteUser(selectedUser.id));
            } else if (modalType === "edit") {
                console.log("RAW BODY BEFORE TO AXIOS:", JSON.stringify(selectedUser));
                dispatch(updateUser({ id: selectedUser.id, user: selectedUser }));
            }
            closeModal();
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const addUser = () => {
        setSelectedUser(EMPTY_USER);
        openModal("add");
    };

    const handleEditUser = (user) => {
        console.log("RAW BODY handleEditUser:", JSON.stringify(selectedUser));
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
                    + Add user
                </button>
            </nav>

            {/* ── Table ── */}
            <section className="tableContainer">
                <table className="table">
                    <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Username</th>
                        <th style={{ width: "250px" }} scope="col">Email</th>
                        <th scope="col">FirstName</th>
                        <th scope="col">LastName</th>
                        <th scope="col">PhoneNumber</th>
                        <th scope="col">Authority</th>
                        <th scope="col">Non Expired</th>
                        <th scope="col">Non Locked</th>
                        <th scope="col">Crds Non Expired</th>
                        <th scope="col">Enabled</th>
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
                            <td>{user.email}</td>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.phoneNumber}</td>
                            <td>{user.authorities
                                ? [...user.authorities].sort((a, b) => a.name.localeCompare(b.name)).map(a => a.name).join(", ")
                                : ""}
                            </td>
                            <td style={{ color: user.accountNonExpired ? "darkgreen" : "tomato" }}>
                                {user.accountNonExpired ? "Yes" : "No"}
                            </td>
                            <td style={{ color: user.accountNonLocked ? "darkgreen" : "tomato" }}>
                                {user.accountNonLocked ? "Yes" : "No"}
                            </td>
                            <td style={{ color: user.credentialsNonExpired ? "darkgreen" : "tomato" }}>
                                {user.credentialsNonExpired ? "Yes" : "No"}
                            </td>
                            <td style={{ color: user.enabled ? "darkgreen" : "tomato" }}>
                                {user.enabled ? "Yes" : "No"}
                            </td>
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
                selectedUser={selectedUser}
                setSelectedUser={setSelectedUser}
                isTreeModalOpen={isTreeModalOpen}
                setIsTreeModalOpen={setIsTreeModalOpen}
                authorities={authorities} />
        </main>
    );

}