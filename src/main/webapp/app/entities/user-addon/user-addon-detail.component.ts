import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { UserAddon } from './user-addon.model';
import { UserAddonService } from './user-addon.service';

@Component({
    selector: 'jhi-user-addon-detail',
    templateUrl: './user-addon-detail.component.html'
})
export class UserAddonDetailComponent implements OnInit, OnDestroy {

    userAddon: UserAddon;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userAddonService: UserAddonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserAddons();
    }

    load(id) {
        this.userAddonService.find(id)
            .subscribe((userAddonResponse: HttpResponse<UserAddon>) => {
                this.userAddon = userAddonResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserAddons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userAddonListModification',
            (response) => this.load(this.userAddon.id)
        );
    }
}
