import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DcAuthority } from './dc-authority.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DcAuthority>;

@Injectable()
export class DcAuthorityService {

    private resourceUrl =  SERVER_API_URL + 'api/dc-authorities';

    constructor(private http: HttpClient) { }

    create(dcAuthority: DcAuthority): Observable<EntityResponseType> {
        const copy = this.convert(dcAuthority);
        return this.http.post<DcAuthority>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dcAuthority: DcAuthority): Observable<EntityResponseType> {
        const copy = this.convert(dcAuthority);
        return this.http.put<DcAuthority>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DcAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DcAuthority[]>> {
        const options = createRequestOption(req);
        return this.http.get<DcAuthority[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DcAuthority[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DcAuthority = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DcAuthority[]>): HttpResponse<DcAuthority[]> {
        const jsonResponse: DcAuthority[] = res.body;
        const body: DcAuthority[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DcAuthority.
     */
    private convertItemFromServer(dcAuthority: DcAuthority): DcAuthority {
        const copy: DcAuthority = Object.assign({}, dcAuthority);
        return copy;
    }

    /**
     * Convert a DcAuthority to a JSON which can be sent to the server.
     */
    private convert(dcAuthority: DcAuthority): DcAuthority {
        const copy: DcAuthority = Object.assign({}, dcAuthority);
        return copy;
    }
}
