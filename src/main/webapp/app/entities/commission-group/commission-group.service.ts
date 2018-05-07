import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CommissionGroup } from './commission-group.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CommissionGroup>;

@Injectable()
export class CommissionGroupService {

    private resourceUrl =  SERVER_API_URL + 'api/commission-groups';

    constructor(private http: HttpClient) { }

    create(commissionGroup: CommissionGroup): Observable<EntityResponseType> {
        const copy = this.convert(commissionGroup);
        return this.http.post<CommissionGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(commissionGroup: CommissionGroup): Observable<EntityResponseType> {
        const copy = this.convert(commissionGroup);
        return this.http.put<CommissionGroup>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CommissionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CommissionGroup[]>> {
        const options = createRequestOption(req);
        return this.http.get<CommissionGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CommissionGroup[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CommissionGroup = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CommissionGroup[]>): HttpResponse<CommissionGroup[]> {
        const jsonResponse: CommissionGroup[] = res.body;
        const body: CommissionGroup[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CommissionGroup.
     */
    private convertItemFromServer(commissionGroup: CommissionGroup): CommissionGroup {
        const copy: CommissionGroup = Object.assign({}, commissionGroup);
        return copy;
    }

    /**
     * Convert a CommissionGroup to a JSON which can be sent to the server.
     */
    private convert(commissionGroup: CommissionGroup): CommissionGroup {
        const copy: CommissionGroup = Object.assign({}, commissionGroup);
        return copy;
    }
}
