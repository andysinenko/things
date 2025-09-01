import {deleteToolFailure, deleteToolSuccess, fetchToolsFailure, fetchToolsStart, fetchToolsSuccess, updateToolFailure} from "../reducer/ToolsSlice";
import axios from "axios";
import {fetchBrandsFailure, fetchBrandsStart, fetchBrandsSuccess} from "../reducer/BrandsSlice";


export const fetchTools = () => async (dispatch) => {
    dispatch(fetchToolsStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/tools");
        dispatch(fetchToolsSuccess(response.data));
    } catch (error) {
        dispatch(fetchToolsFailure(error.message));
    }
};

export const addNewTool = (tool) => async (dispatch) => {
    try {
        const response = await axios.post("http://localhost:8080/api/v1/tools", tool);
        dispatch(fetchTools());
    } catch (error) {
        dispatch(fetchToolsFailure(error.message));
    }
};

export const updateTool = (id, tool) => async (dispatch) => {
    try {
        console.log("EDITED TOOL ID: ", id);
        console.log("EDITED TOOL: ", tool);
        const response = await axios.put(`http://localhost:8080/api/v1/tools/${id}`, tool);
        dispatch(fetchTools());
    } catch (error) {
        dispatch(updateToolFailure(error.message));
    }
};

export const deleteTool = (id) => async (dispatch) => {
    try {
        const response = await axios.delete(`http://localhost:8080/api/v1/tools/${id}`);
        if (response.status === 200) {
            dispatch(deleteToolSuccess(id));
        } else {
            dispatch(deleteToolFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        dispatch(deleteToolFailure(error.message));
    }
};

export const fetchBrands = () => async (dispatch) => {
    dispatch(fetchBrandsStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/tools/brands");
        dispatch(fetchBrandsSuccess(response.data));
    } catch (error) {
        dispatch(fetchBrandsFailure(error.message));
    }
};