import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserAddon } from './user-addon.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserAddon>;

@Injectable()
export class UserAddonService {

    private resourceUrl =  SERVER_API_URL + 'api/user-addons';

    constructor(private http: HttpClient) { }

    create(userAddon: UserAddon): Observable<EntityResponseType> {
        const copy = this.convert(userAddon);
        return this.http.post<UserAddon>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userAddon: UserAddon): Observable<EntityResponseType> {
        const copy = this.convert(userAddon);
        return this.http.put<UserAddon>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserAddon>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserAddon[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserAddon[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserAddon[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserAddon = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserAddon[]>): HttpResponse<UserAddon[]> {
        const jsonResponse: UserAddon[] = res.body;
        const body: UserAddon[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserAddon.
     */
    private convertItemFromServer(userAddon: UserAddon): UserAddon {
        const copy: UserAddon = Object.assign({}, userAddon);
        return copy;
    }

    /**
     * Convert a UserAddon to a JSON which can be sent to the server.
     */
    private convert(userAddon: UserAddon): UserAddon {
        const copy: UserAddon = Object.assign({}, userAddon);
        return copy;
    }
}
