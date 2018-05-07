import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserAddon } from './user-addon.model';
import { UserAddonPopupService } from './user-addon-popup.service';
import { UserAddonService } from './user-addon.service';
import { User, UserService } from '../../shared';
import { CommissionGroup, CommissionGroupService } from '../commission-group';

@Component({
    selector: 'jhi-user-addon-dialog',
    templateUrl: './user-addon-dialog.component.html'
})
export class UserAddonDialogComponent implements OnInit {

    userAddon: UserAddon;
    isSaving: boolean;
    authorities: any[];
    users: User[];

    commissiongroups: CommissionGroup[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userAddonService: UserAddonService,
        private userService: UserService,
        private commissionGroupService: CommissionGroupService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.commissionGroupService.query()
            .subscribe((res: HttpResponse<CommissionGroup[]>) => { this.commissiongroups = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.authorities = [];
        this.userService.authorities().subscribe((authorities) => {
            this.authorities = authorities;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userAddon.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userAddonService.update(this.userAddon));
        } else {
            this.subscribeToSaveResponse(
                this.userAddonService.create(this.userAddon));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<UserAddon>>) {
        result.subscribe((res: HttpResponse<UserAddon>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: UserAddon) {
        this.eventManager.broadcast({ name: 'userAddonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCommissionGroupById(index: number, item: CommissionGroup) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-addon-popup',
    template: ''
})
export class UserAddonPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAddonPopupService: UserAddonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userAddonPopupService
                    .open(UserAddonDialogComponent as Component, params['id']);
            } else {
                this.userAddonPopupService
                    .open(UserAddonDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
