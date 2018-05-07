/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SboxTestModule } from '../../../test.module';
import { UserAddonComponent } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.component';
import { UserAddonService } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.service';
import { UserAddon } from '../../../../../../main/webapp/app/entities/user-addon/user-addon.model';

describe('Component Tests', () => {

    describe('UserAddon Management Component', () => {
        let comp: UserAddonComponent;
        let fixture: ComponentFixture<UserAddonComponent>;
        let service: UserAddonService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [UserAddonComponent],
                providers: [
                    UserAddonService
                ]
            })
            .overrideTemplate(UserAddonComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserAddonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserAddonService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new UserAddon(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userAddons[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
