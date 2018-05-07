import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Api } from './api.model';
import { ApiPopupService } from './api-popup.service';
import { ApiService } from './api.service';

@Component({
    selector: 'jhi-api-delete-dialog',
    templateUrl: './api-delete-dialog.component.html'
})
export class ApiDeleteDialogComponent {

    api: Api;

    constructor(
        private apiService: ApiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.apiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'apiListModification',
                content: 'Deleted an api'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-delete-popup',
    template: ''
})
export class ApiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apiPopupService: ApiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.apiPopupService
                .open(ApiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
