import {useDispatch} from "react-redux";
import React, {useEffect} from "react";
import {clearAuthorities, fetchAuthorities} from "../reducer/AuthoritiesSlice.jsx";


const AdminModal = ({
                        isOpen,
                        onClose,
                        onSubmit,
                        modalType,
                        selectedUser,
                        setSelectedUser,
                        isTreeModalOpen,
                        setIsTreeModalOpen,
                        authorities
                   }) => {

    const dispatch = useDispatch();

    const handleChange = (e) => {
        const {name, value} = e.target;
        setSelectedUser((prev) => ({...prev, [name]: value}));
    };

    useEffect(() => {
        if (isOpen) {
            dispatch(fetchAuthorities());
        } else {
            dispatch(clearAuthorities());
        }
    }, [isOpen, dispatch]);

    if (!isOpen) return null;

    const renderEditAdd = () => (
        <>
            <div className="th-modal-header">
                <span className="th-modal-title">
                    {modalType === "add" ? "Add user" : "Edit user"}
                </span>
                <button className="th-modal-close-btn" onClick={onClose} aria-label="Close">
                    ✖️
                </button>
            </div>

            <div className="modal-body">
                {/* username + password */}
                <div className="modal-field-row">
                    <div className="modal-field">
                        <label>Username</label>
                        <input
                            className="modal-input"
                            type="text"
                            name="title"
                            value={selectedUser.username}
                            onChange={handleChange}
                            placeholder="Username"
                            maxLength="64"
                        />
                    </div>
                    <div className="modal-field">
                        <label>Password</label>
                        <input
                            className="modal-input"
                            type="password"
                            name="password"
                            value={selectedUser.password}
                            onChange={handleChange}
                            placeholder="password"
                            maxLength="64"
                        />
                    </div>
                </div>

                {/* email + first name */}
                <div className="modal-field-row">
                    <div className="modal-field">
                        <label>Email</label>
                        <input
                            className="modal-input"
                            type="email"
                            name="email"
                            value={selectedUser.email}
                            onChange={handleChange}
                            placeholder="email"
                            maxLength="64"
                        />
                    </div>
                    <div className="modal-field">
                        <label>First name</label>
                        <input
                            className="modal-input"
                            type="text"
                            name="firstname"
                            value={selectedUser.firstName}
                            onChange={handleChange}
                            placeholder="First name"
                            maxLength="64"
                        />
                    </div>
                </div>

                {/* Last name + phone number */}
                <div className="modal-field-row">
                    <div className="modal-field">
                        <label>Last name</label>
                        <input
                            className="modal-input"
                            type="text"
                            name="lastname"
                            value={selectedUser.lastName}
                            onChange={handleChange}
                            placeholder="Last name"
                            maxLength="64"
                        />
                    </div>
                    <div className="modal-field">
                        <label>Phone number</label>
                        <input
                            className="modal-input"
                            type="text"
                            name="phonenumber"
                            value={selectedUser.phoneNumber}
                            onChange={handleChange}
                            placeholder="Last name"
                            maxLength="64"
                        />
                    </div>
                </div>
                <div className="modal-field">
                    <label>Authorities</label>
                    <select
                        multiple
                        value={selectedUser.authorities?.map(a => String(a.id)) ?? []}
                        onChange={(e) => {
                            const selectedIds = Array.from(e.target.selectedOptions, o => Number(o.value));
                            const picked = authorities.filter(a => selectedIds.includes(a.id));
                            setSelectedUser(prev => ({ ...prev, authorities: picked }));
                        }}
                    >
                        {authorities.map((a) => (
                            <option key={a.id} value={a.id}>{a.name}</option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="modal-footer">
                <button className="modal-button" onClick={onClose}>Cancel</button>
                <button className="modal-button modal-btn-primary" onClick={onSubmit}>Save</button>
            </div>
        </>
    );

    const renderDelete = () => (
        <>
            <div className="th-modal-header">
                <span className="th-modal-title">Delete user</span>
                <button className="th-modal-close-btn" onClick={onClose} aria-label="Close">
                    ✕
                </button>
            </div>
            <div className="modal-body">
                <p style={{color: "#4b5563", fontSize: 14, lineHeight: 1.5}}>
                    Are you sure you want to delete{" "}
                    <strong style={{color: "#1a2332"}}>"{selectedUser?.username}"</strong>?
                </p>
            </div>
            <div className="modal-footer">
                <button className="modal-button" onClick={onClose}>
                    Cancel
                </button>
                <button
                    className="modal-button"
                    onClick={onSubmit}
                    style={{
                        background: "#b91c1c",
                        color: "#fff",
                        border: "1px solid #b91c1c",
                    }}
                >
                    Delete
                </button>
            </div>
        </>
    );

    return (
        <>
            <div className="th-modal-overlay">
                <div className="th-modal-content">
                    {(modalType === "add" || modalType === "edit") && renderEditAdd()}
                    {modalType === "delete" && renderDelete()}
                </div>
            </div>
        </>
    );
}
export default AdminModal;