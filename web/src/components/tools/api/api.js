import {fetchToolsFailure, fetchToolsStart, fetchToolsSuccess} from "../reducer/ToolsSlice";
import axios from "axios";


export const fetchTools = async (dispatch) => {
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

export const addNewTool = (dispatch, tool) => {
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