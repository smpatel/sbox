import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Api } from './api.model';
import { ApiService } from './api.service';

@Component({
    selector: 'jhi-api-detail',
    templateUrl: './api-detail.component.html'
})
export class ApiDetailComponent implements OnInit, OnDestroy {

    api: Api;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private apiService: ApiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInApis();
    }

    load(id) {
        this.apiService.find(id)
            .subscribe((apiResponse: HttpResponse<Api>) => {
                this.api = apiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInApis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'apiListModification',
            (response) => this.load(this.api.id)
        );
    }
}
