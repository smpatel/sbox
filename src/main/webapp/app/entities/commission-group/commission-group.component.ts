import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CommissionGroup } from './commission-group.model';
import { CommissionGroupService } from './commission-group.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-commission-group',
    templateUrl: './commission-group.component.html'
})
export class CommissionGroupComponent implements OnInit, OnDestroy {
commissionGroups: CommissionGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private commissionGroupService: CommissionGroupService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.commissionGroupService.query().subscribe(
            (res: HttpResponse<CommissionGroup[]>) => {
                this.commissionGroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCommissionGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CommissionGroup) {
        return item.id;
    }
    registerChangeInCommissionGroups() {
        this.eventSubscriber = this.eventManager.subscribe('commissionGroupListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
