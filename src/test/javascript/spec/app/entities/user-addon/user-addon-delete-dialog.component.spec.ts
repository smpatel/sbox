/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SboxTestModule } from '../../../test.module';
import { UserAddonDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/user-addon/user-addon-delete-dialog.component';
import { UserAddonService } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.service';

describe('Component Tests', () => {

    describe('UserAddon Management Delete Component', () => {
        let comp: UserAddonDeleteDialogComponent;
        let fixture: ComponentFixture<UserAddonDeleteDialogComponent>;
        let service: UserAddonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [UserAddonDeleteDialogComponent],
                providers: [
                    UserAddonService
                ]
            })
            .overrideTemplate(UserAddonDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAddonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddonService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
