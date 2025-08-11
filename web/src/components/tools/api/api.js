import {fetchToolsFailure, fetchToolsStart, fetchToolsSuccess} from "../reducer/ToolsSlice";
import axios from "axios";
import {fetchBrandsFailure, fetchBrandsStart, fetchBrandsSuccess} from "../reducer/BrandsSlice";


export const fetchTools = () => async (dispatch) => {
    dispatch(fetchToolsStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/tools");
        if (response.status === 200) {
            console.log("Success on fetching tools: ", response.status);
            dispatch(fetchToolsSuccess(response.data));
        } else {
            console.log("Error on fetching tools: ", response.status);
            dispatch(fetchToolsFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching tool, catch section: ", error.message);
        dispatch(fetchToolsFailure(error.message));
    }
};

export const addNewTool = (dispatch, tool) => async(dispatch) => {
    axios.post("http://localhost:8080/api/v1/tools", tool)
        .then(response => {
            if (response.status === 200) {
                console.log("Success on adding new tool: ", response.status);
                dispatch(fetchToolsSuccess(response.data));
            } else {
                console.log("Error on adding new tool: ", response.status);
                dispatch(fetchToolsFailure(`Error: ${response.status}`));
            }
        })
        .catch(error => {
            console.log("Error on adding new tool, catch section: ", error.message);
            dispatch(fetchToolsFailure(error.message));
        });
};

export const fetchBrands = () => async (dispatch) => {
    dispatch(fetchBrandsStart());
    try {
        const response = await axios.get("http://localhost:8080/api/v1/tools/brands");
        if (response.status === 200) {
            console.log("Success on fetching brands: ", response.status);
            dispatch(fetchBrandsSuccess(response.data));
        } else {
            console.log("Error on fetching brands: ", response.status);
            dispatch(fetchBrandsFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching brands, catch section: ", error.message);
        if (error.config) {
            console.log("Request headers on error:", error.config.headers);
        }
        dispatch(fetchBrandsFailure(error.message));
    }
};
