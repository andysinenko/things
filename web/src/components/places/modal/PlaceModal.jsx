import React, {useEffect, useState} from "react";
import {TreeView} from "../treeview/TreeView";

const PlaceModal = ({
                        places,
                        onCrossClick,
                        isOpen,
                        onClose,
                        onSubmit,
                        onAddChild
                    }) =>
{
    const [data, setData] = useState(null);


    useEffect(() => {
        console.log("PLACES 5 PlaceModal: ", places);
        const res = buildTree(places);

        console.log("DATA: ", res);
    }, [places]);


    const buildTree = (places) => {
        console.log("PLACES 6 PlaceModal: ", places);
        const map = new Map();
        const roots = [];

        places.forEach(item => {
            map.set(item.id, {...item, children: []});
        });

        map.forEach(item => {
            if (item.parent && map.has(item.parent.id)) {
                map.get(item.parent.id).children.push(item);
            } else {
                roots.push(item);
            }
        });
        console.log("roots 7 PlaceModal: ", roots);
        setData(roots);
        return roots;
    };



    if (!isOpen) return null;
    const renderContent = () => {
        return (
            <div className="th-modal-overlay">
                <div className="th-modal-content">
                    <div className="th-modal-header">
                        <h5>Add place</h5>
                        <button className="th-modal-close-btn" onClick={onClose}>×</button>
                    </div>

                    <div className="modal-body">
                        <TreeView data={data} onCrossClick={onCrossClick} onAddChild={onAddChild}/>
                    </div>

                    <div className="modal-footer">
                        <button className="th-main-button" onClick={onClose}>Close</button>
                        <button className="th-main-button" onClick={onSubmit}>Save Changes</button>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="th-modal-overlay">
            <div className="th-modal-content">{renderContent()}</div>
        </div>
    );
};

export default PlaceModal;