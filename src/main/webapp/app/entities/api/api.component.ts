import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Api } from './api.model';
import { ApiService } from './api.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-api',
    templateUrl: './api.component.html'
})
export class ApiComponent implements OnInit, OnDestroy {
apis: Api[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private apiService: ApiService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.apiService.query().subscribe(
            (res: HttpResponse<Api[]>) => {
                this.apis = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInApis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Api) {
        return item.id;
    }
    registerChangeInApis() {
        this.eventSubscriber = this.eventManager.subscribe('apiListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
