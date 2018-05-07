import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Api } from './api.model';
import { ApiPopupService } from './api-popup.service';
import { ApiService } from './api.service';

@Component({
    selector: 'jhi-api-dialog',
    templateUrl: './api-dialog.component.html'
})
export class ApiDialogComponent implements OnInit {

    api: Api;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private apiService: ApiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.api.id !== undefined) {
            this.subscribeToSaveResponse(
                this.apiService.update(this.api));
        } else {
            this.subscribeToSaveResponse(
                this.apiService.create(this.api));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Api>>) {
        result.subscribe((res: HttpResponse<Api>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Api) {
        this.eventManager.broadcast({ name: 'apiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-api-popup',
    template: ''
})
export class ApiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apiPopupService: ApiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.apiPopupService
                    .open(ApiDialogComponent as Component, params['id']);
            } else {
                this.apiPopupService
                    .open(ApiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
