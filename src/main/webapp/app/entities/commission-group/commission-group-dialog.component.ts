import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CommissionGroup } from './commission-group.model';
import { CommissionGroupPopupService } from './commission-group-popup.service';
import { CommissionGroupService } from './commission-group.service';
import { Operator, OperatorService } from '../operator';

@Component({
    selector: 'jhi-commission-group-dialog',
    templateUrl: './commission-group-dialog.component.html'
})
export class CommissionGroupDialogComponent implements OnInit {

    commissionGroup: CommissionGroup;
    isSaving: boolean;

    operators: Operator[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commissionGroupService: CommissionGroupService,
        private operatorService: OperatorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.operatorService.query()
            .subscribe((res: HttpResponse<Operator[]>) => { this.operators = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commissionGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commissionGroupService.update(this.commissionGroup));
        } else {
            this.subscribeToSaveResponse(
                this.commissionGroupService.create(this.commissionGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CommissionGroup>>) {
        result.subscribe((res: HttpResponse<CommissionGroup>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CommissionGroup) {
        this.eventManager.broadcast({ name: 'commissionGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackOperatorById(index: number, item: Operator) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-commission-group-popup',
    template: ''
})
export class CommissionGroupPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commissionGroupPopupService: CommissionGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commissionGroupPopupService
                    .open(CommissionGroupDialogComponent as Component, params['id']);
            } else {
                this.commissionGroupPopupService
                    .open(CommissionGroupDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
