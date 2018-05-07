import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Operator } from './operator.model';
import { OperatorPopupService } from './operator-popup.service';
import { OperatorService } from './operator.service';
import { Api, ApiService } from '../api';

@Component({
    selector: 'jhi-operator-dialog',
    templateUrl: './operator-dialog.component.html'
})
export class OperatorDialogComponent implements OnInit {

    operator: Operator;
    isSaving: boolean;

    apis: Api[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private operatorService: OperatorService,
        private apiService: ApiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.apiService.query()
            .subscribe((res: HttpResponse<Api[]>) => { this.apis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operator.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operatorService.update(this.operator));
        } else {
            this.subscribeToSaveResponse(
                this.operatorService.create(this.operator));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Operator>>) {
        result.subscribe((res: HttpResponse<Operator>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Operator) {
        this.eventManager.broadcast({ name: 'operatorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackApiById(index: number, item: Api) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-operator-popup',
    template: ''
})
export class OperatorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operatorPopupService: OperatorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.operatorPopupService
                    .open(OperatorDialogComponent as Component, params['id']);
            } else {
                this.operatorPopupService
                    .open(OperatorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
