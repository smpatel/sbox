/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SboxTestModule } from '../../../test.module';
import { UserAddonDialogComponent } from '../../../../../../main/webapp/app/entities/user-addon/user-addon-dialog.component';
import { UserAddonService } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.service';
import { UserAddon } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { CommissionGroupService } from '../../../../../../main/webapp/app/entities/commission-group';

describe('Component Tests', () => {

    describe('UserAddon Management Dialog Component', () => {
        let comp: UserAddonDialogComponent;
        let fixture: ComponentFixture<UserAddonDialogComponent>;
        let service: UserAddonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [UserAddonDialogComponent],
                providers: [
                    UserService,
                    CommissionGroupService,
                    UserAddonService
                ]
            })
            .overrideTemplate(UserAddonDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAddonDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddonService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserAddon(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userAddon = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userAddonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserAddon();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.userAddon = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userAddonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
