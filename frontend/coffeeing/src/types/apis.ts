export interface ApiSuccessResponse<D> {
    code: string;
    message: string;
    data: D;
}

export interface ApiErrorResponse<D> {
    code: string;
    message: string;
    data?: null;
}

export type ToggleResult = {
    result: boolean
}

export type ApiResponse<D> = ApiSuccessResponse<D> | ApiErrorResponse<D>;