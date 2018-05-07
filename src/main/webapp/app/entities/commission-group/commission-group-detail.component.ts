import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CommissionGroup } from './commission-group.model';
import { CommissionGroupService } from './commission-group.service';

@Component({
    selector: 'jhi-commission-group-detail',
    templateUrl: './commission-group-detail.component.html'
})
export class CommissionGroupDetailComponent implements OnInit, OnDestroy {

    commissionGroup: CommissionGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commissionGroupService: CommissionGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommissionGroups();
    }

    load(id) {
        this.commissionGroupService.find(id)
            .subscribe((commissionGroupResponse: HttpResponse<CommissionGroup>) => {
                this.commissionGroup = commissionGroupResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommissionGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commissionGroupListModification',
            (response) => this.load(this.commissionGroup.id)
        );
    }
}
