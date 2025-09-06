import React, { useState } from 'react';
import './TreeView.css';

const TreeNode = ({ node, onCrossClick, onAddChild}) => {
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

    const handleChoosePlase = (node) => {
        onCrossClick(node);
    }

    return (
        <li className="tree-node">
            <div className="tree-node-header">
                {node.level != 3 ?
                (<button
                    className="tree-toggle-btn"
                    onClick={() => setExpanded(!expanded)}
                    title="Toggle children">
                    {expanded ? '🔽' : '▶️'}
                </button>) : null}

                <span className="tree-node-name" title={`level: ${node.level} - ${node.description}`}>
                    {node.level === 3 ? ` ${node.name}` : node.name}
                </span>

                {node.level === 3 ?
                    (<button title="Choose this plase" type="button" onClick={() => handleChoosePlase(node)} className="tree-remove-btn">⚽️</button>)
                    : (<button title="Add a child place" type="button" onClick={handleAddClick} className="tree-add-btn">+</button>
                )}
            </div>

            {showInput && (
                <form onSubmit={handleInputSubmit} className="tree-input-form">
                    <input type="text" value={childName} onChange={handleInputChange} placeholder="Child place name" className="tree-input"/>
                    <div className="th-buttons-toolbar">
                        <button type="submit" className="tree-confirm-btn">Add</button>
                        <button type="button" className="tree-cancel-btn" onClick={() => setShowInput(false)}>Cancel</button>
                    </div>
                </form>
            )}

            {hasChildren && expanded && (
                <ul className="tree-children">
                    {node.children.map(child =>
                        (<TreeNode key={child.id} node={child} onCrossClick={onCrossClick} onAddChild={onAddChild} />)
                    )}
                </ul>
            )}
        </li>
    );
};

export const TreeView = ({ data, onCrossClick, onAddChild }) => {
    console.log("DATA from TreeView: ", data);
    return (
        <ul className="tree-view">
            {data.map(node => (
                <TreeNode
                    key={node.id}
                    node={node}
                    onCrossClick={onCrossClick}
                    onAddChild={onAddChild}
                />
            ))}
        </ul>
    );
};