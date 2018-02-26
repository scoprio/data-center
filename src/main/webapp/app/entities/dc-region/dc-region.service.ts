import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DcRegion } from './dc-region.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DcRegion>;

@Injectable()
export class DcRegionService {

    private resourceUrl =  SERVER_API_URL + 'api/dc-regions';

    constructor(private http: HttpClient) { }

    create(dcRegion: DcRegion): Observable<EntityResponseType> {
        const copy = this.convert(dcRegion);
        return this.http.post<DcRegion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dcRegion: DcRegion): Observable<EntityResponseType> {
        const copy = this.convert(dcRegion);
        return this.http.put<DcRegion>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DcRegion>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DcRegion[]>> {
        const options = createRequestOption(req);
        return this.http.get<DcRegion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DcRegion[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DcRegion = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DcRegion[]>): HttpResponse<DcRegion[]> {
        const jsonResponse: DcRegion[] = res.body;
        const body: DcRegion[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DcRegion.
     */
    private convertItemFromServer(dcRegion: DcRegion): DcRegion {
        const copy: DcRegion = Object.assign({}, dcRegion);
        return copy;
    }

    /**
     * Convert a DcRegion to a JSON which can be sent to the server.
     */
    private convert(dcRegion: DcRegion): DcRegion {
        const copy: DcRegion = Object.assign({}, dcRegion);
        return copy;
    }
}
