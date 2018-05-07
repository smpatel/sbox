import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CommissionGroup } from './commission-group.model';
import { CommissionGroupPopupService } from './commission-group-popup.service';
import { CommissionGroupService } from './commission-group.service';

@Component({
    selector: 'jhi-commission-group-delete-dialog',
    templateUrl: './commission-group-delete-dialog.component.html'
})
export class CommissionGroupDeleteDialogComponent {

    commissionGroup: CommissionGroup;

    constructor(
        private commissionGroupService: CommissionGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commissionGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commissionGroupListModification',
                content: 'Deleted an commissionGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commission-group-delete-popup',
    template: ''
})
export class CommissionGroupDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commissionGroupPopupService: CommissionGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commissionGroupPopupService
                .open(CommissionGroupDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
