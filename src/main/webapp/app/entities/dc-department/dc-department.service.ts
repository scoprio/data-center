import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DcDepartment } from './dc-department.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DcDepartment>;

@Injectable()
export class DcDepartmentService {

    private resourceUrl =  SERVER_API_URL + 'api/dc-departments';

    constructor(private http: HttpClient) { }

    create(dcDepartment: DcDepartment): Observable<EntityResponseType> {
        const copy = this.convert(dcDepartment);
        return this.http.post<DcDepartment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dcDepartment: DcDepartment): Observable<EntityResponseType> {
        const copy = this.convert(dcDepartment);
        return this.http.put<DcDepartment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DcDepartment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DcDepartment[]>> {
        const options = createRequestOption(req);
        return this.http.get<DcDepartment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DcDepartment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DcDepartment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DcDepartment[]>): HttpResponse<DcDepartment[]> {
        const jsonResponse: DcDepartment[] = res.body;
        const body: DcDepartment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DcDepartment.
     */
    private convertItemFromServer(dcDepartment: DcDepartment): DcDepartment {
        const copy: DcDepartment = Object.assign({}, dcDepartment);
        return copy;
    }

    /**
     * Convert a DcDepartment to a JSON which can be sent to the server.
     */
    private convert(dcDepartment: DcDepartment): DcDepartment {
        const copy: DcDepartment = Object.assign({}, dcDepartment);
        return copy;
    }
}
