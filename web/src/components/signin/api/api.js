import axios from "axios";
import {fetchUserFailure, fetchUserStart, fetchUserSuccess} from "../reducer/UserSlice";


export const fetchUser = async (dispatch) => {
    dispatch(fetchUserStart());
    try {
        const response = await axios.post("http://localhost:8080/api/v1/auth/user");
        if (response.status === 200) {
            console.log("Success on fetching user data: ", response.status);
            dispatch(fetchUserSuccess(response.data));
        } else {
            console.log("Error on fetching user data: ", response.status);
            dispatch(fetchUserFailure(`Error: ${response.status}`));
        }
    } catch (error) {
        console.log("Error on fetching user data, catch section: ", error.message);
        dispatch(fetchUserFailure(error.message));
    }
};
