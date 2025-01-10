import axios from "axios";

export const get = ({
                        url,
                        params,
                        dispatch,
                        successCallBack,
                        failureCallback,
                        config
                    }) => {

    return axios.get(url, config);
};

/*export const post = () => {

};

export const put = () => {

};

export const delete = () => {

};*/
