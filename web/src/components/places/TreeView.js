import React, {useState} from 'react';
import {ListGroup, Button, FormControl} from 'react-bootstrap';

const TreeNode = ({node, onCrossClick, onAddChild}) => {
    const [expanded, setExpanded] = useState(false);
    const [showInput, setShowInput] = useState(false);
    const [childName, setChildName] = useState('');
    const hasChildren = node.children && node.children.length > 0;

    const handleAddClick = () => setShowInput(true);

    const handleInputChange = (e) => setChildName(e.target.value);

    const handleInputSubmit = (e) => {
        e.preventDefault();
        if (childName.trim()) {
            onAddChild(node, childName);
            setChildName('');
            setShowInput(false);
        }
    };


    return (
        <ListGroup.Item className="p-1">
            <div className="d-flex align-items-center">
                {hasChildren && (
                    <div className="ms-3 mt-1" style={{cursor: 'pointer'}}>
                        <Button variant="link" size="sm" onClick={() => setExpanded(!expanded)}
                                className="rounded-circle">{expanded ? '🔽' : '▶️'}</Button>
                    </div>
                )}
                {!hasChildren && node.level !== 3 && (
                    <div className="ms-3 mt-1" style={{cursor: 'pointer'}}>
                        <Button variant="link" size="sm" onClick={() => setExpanded(!expanded)}
                                className="rounded-circle">{expanded ? '⛔️' : '⛔️'}</Button>
                    </div>
                )}
                {node.level !== 3 && (<span title={`level: ${node.level} - ${node.description}`} className="ms-3 mt-1">
                    {node.name}
                </span>)}
                {node.level === 3 && (
                    <div>
                        <span title={`level: ${node.level} - ${node.description}`} className="ms-3 mt-1">
                           ➖ {node.name}
                        </span>
                        <Button title="Add a child place"
                                variant="light"
                                size="sm" style={{width: "30px", height: "20px", padding: "0", fontWeight: "bold"}}
                                onClick={(event) => onCrossClick(event, node)}
                                className="p-0 me-0 rounded-circle mx-3 ">⛔️</Button>
                    </div>
                )}
                {node.level !== 3 && (
                    <Button
                        title="Add a child place"
                        variant="light"
                        size="sm"
                        style={{width: "30px", height: "20px", padding: "0", fontWeight: "bold"}}
                        onClick={handleAddClick}
                        className="p-0 me-0 rounded-circle mx-3"
                    >➕</Button>
                )}
            </div>
            {showInput && (
                <form onSubmit={handleInputSubmit} className="d-flex align-items-center ms-3 mt-2">
                    <FormControl
                        type="text"
                        value={childName}
                        onChange={handleInputChange}
                        placeholder="Child place name"
                        size="sm"
                        className="me-2"
                    />
                    <div className="th-buttons-toolbar">
                    <Button type="submit" size="sm" variant="success">Add</Button>
                    <Button size="sm" variant="secondary" onClick={() => setShowInput(false)} className="ms-2">Cancel</Button>
                    </div>
                </form>
            )}

            {hasChildren && expanded && (
                <ListGroup className="ms-3">
                    {node.children.map(child => (
                        <TreeNode key={child.id} node={child} onCrossClick={onCrossClick}/>
                    ))}
                </ListGroup>
            )}
        </ListGroup.Item>
    );
};

export const TreeView = ({data, onCrossClick, onAddChild}) => {
    return (
        <ListGroup>
            {data.map(node => (
                <TreeNode key={node.id} node={node} onCrossClick={onCrossClick} onAddChild={onAddChild}/>
            ))}
        </ListGroup>
    );
};