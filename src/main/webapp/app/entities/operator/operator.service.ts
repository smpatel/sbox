import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Operator } from './operator.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Operator>;

@Injectable()
export class OperatorService {

    private resourceUrl =  SERVER_API_URL + 'api/operators';

    constructor(private http: HttpClient) { }

    create(operator: Operator): Observable<EntityResponseType> {
        const copy = this.convert(operator);
        return this.http.post<Operator>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(operator: Operator): Observable<EntityResponseType> {
        const copy = this.convert(operator);
        return this.http.put<Operator>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Operator>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Operator[]>> {
        const options = createRequestOption(req);
        return this.http.get<Operator[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Operator[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Operator = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Operator[]>): HttpResponse<Operator[]> {
        const jsonResponse: Operator[] = res.body;
        const body: Operator[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Operator.
     */
    private convertItemFromServer(operator: Operator): Operator {
        const copy: Operator = Object.assign({}, operator);
        return copy;
    }

    /**
     * Convert a Operator to a JSON which can be sent to the server.
     */
    private convert(operator: Operator): Operator {
        const copy: Operator = Object.assign({}, operator);
        return copy;
    }
}
