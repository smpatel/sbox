import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Api } from './api.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Api>;

@Injectable()
export class ApiService {

    private resourceUrl =  SERVER_API_URL + 'api/apis';

    constructor(private http: HttpClient) { }

    create(api: Api): Observable<EntityResponseType> {
        const copy = this.convert(api);
        return this.http.post<Api>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(api: Api): Observable<EntityResponseType> {
        const copy = this.convert(api);
        return this.http.put<Api>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Api>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Api[]>> {
        const options = createRequestOption(req);
        return this.http.get<Api[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Api[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Api = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Api[]>): HttpResponse<Api[]> {
        const jsonResponse: Api[] = res.body;
        const body: Api[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Api.
     */
    private convertItemFromServer(api: Api): Api {
        const copy: Api = Object.assign({}, api);
        return copy;
    }

    /**
     * Convert a Api to a JSON which can be sent to the server.
     */
    private convert(api: Api): Api {
        const copy: Api = Object.assign({}, api);
        return copy;
    }
}
