import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserAddon } from './user-addon.model';
import { UserAddonPopupService } from './user-addon-popup.service';
import { UserAddonService } from './user-addon.service';

@Component({
    selector: 'jhi-user-addon-delete-dialog',
    templateUrl: './user-addon-delete-dialog.component.html'
})
export class UserAddonDeleteDialogComponent {

    userAddon: UserAddon;

    constructor(
        private userAddonService: UserAddonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userAddonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userAddonListModification',
                content: 'Deleted an userAddon'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-addon-delete-popup',
    template: ''
})
export class UserAddonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userAddonPopupService: UserAddonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userAddonPopupService
                .open(UserAddonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
