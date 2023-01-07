export const SESSION_INFO: SessionInfo = getSessionInfo();

export type SessionInfo = {
    API_ENDPOINT: string,
    IMAGE_MAX_SIZE: number,
}

function getSessionInfo(): SessionInfo {
    return {
        API_ENDPOINT: import.meta.env.VITE_API_ENDPOINT,
        IMAGE_MAX_SIZE: import.meta.env.VITEA_IMAGE_MAX_SIZE,
    };
}
