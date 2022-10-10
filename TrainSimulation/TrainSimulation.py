from cmath import rect
from email.mime import image
from gettext import find
import sys
import pygame


class Person(pygame.sprite.Sprite):
    def __init__(self):
        super().__init__()
        person_1 = pygame.image.load('graphics/person1.png').convert_alpha()
        person_2 = pygame.image.load('graphics/person2.png').convert_alpha()
        person_1 = pygame.transform.scale(person_1, (25, 50))
        person_2 = pygame.transform.scale(person_2, (25, 50))
        self.frames = [person_1, person_2]

        self.animation_index = 0
        self.image = self.frames[self.animation_index]
        self.rect = self.image.get_rect(
            topleft=(680, 700)
        )

    def animation_state(self):
        self.animation_index += 0.1
        if self.animation_index >= len(self.frames):
            self.animation_index = 0
        self.image = self.frames[int(self.animation_index)]

    def update(self):
        self.animation_state()
        self.rect.x -= 1
        self.destroy()
        self.getX()

    def getX(self):
        self.position = self.rect.x

    def destroy(self):
        if self.rect.x <= -100:
            self.kill()


pygame.init()
clock = pygame.time.Clock()
screen = pygame.display.set_mode((800, 800))
pygame.display.set_caption("Train Simulation")


# Groups
person = pygame.sprite.GroupSingle()
person.add(Person())

# train
train = pygame.image.load('graphics/trainNew.png').convert_alpha()
train = pygame.transform.scale(train, (40, 155))
train_rect = train.get_rect(topleft=(200, 10))

# railway
# railway = pygame.image.load('graphics/railway.png')
# railway = pygame.transform.scale(railway, (100, 800))
# railway_rect = railway.get_rect(topleft=(400, 0))

# roads
roadFromLeft = pygame.Surface([500, 500])
roadFromLeft.fill((128, 128, 118))
roadFromLeft_rect = roadFromLeft.get_rect(topleft=(0, 200))
# person

# sensor light
red_lightT = pygame.Surface([8, 8])
red_lightT.fill((0, 150, 100))
red_light_rect_T = red_lightT.get_rect(topleft=(592, 200))

red_light_rect_T1 = red_lightT.get_rect(topleft=(592, 400))

red_lightB = pygame.Surface([8, 8])
red_lightB.fill((0, 150, 100))
red_light_rect_T2 = red_lightB.get_rect(topleft=(592, 600))


trigger_light = pygame.Surface([8, 8])
trigger_light.fill((230, 10, 10))
trigger_light_rect = trigger_light.get_rect(topleft=(592, 600))
# dark road

# grey road


roadFromTop = pygame.Surface([50, 800])
roadFromTop.fill((202, 164, 114))
roadFromTop_rect = roadFromTop.get_rect(topleft=(200, 0))


trainRoad = pygame.image.load('graphics/track.png').convert_alpha()
trainRoad = pygame.transform.scale(trainRoad, (800, 75))

trainRoad_rect = trainRoad.get_rect(topleft=(0, 400))

while True:

    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            exit()

    screen.fill((86, 125, 70))
    screen.blit(red_lightT, red_light_rect_T)
    screen.blit(red_lightT, red_light_rect_T1)
    screen.blit(red_lightT, red_light_rect_T2)
    screen.blit(roadFromTop, roadFromTop_rect)
    pygame.draw.line(roadFromTop, 'grey', (46, 0), (46, 800), 4)
    pygame.draw.line(roadFromTop, 'grey', (0, 0), (0, 800), 4)

    pygame.draw.line(screen, 'gray', (600, 0), (600, 800))
    person.draw(screen)

    if person.sprite.rect.left <= 600:
        screen.blit(trigger_light, trigger_light_rect)
        train_rect.y += 0
        person.sprite.rect.left += 0
    else:
        train_rect.y += 5
        person.update()

    # screen.blit(railway, railway_rect)
    screen.blit(train, train_rect)
    # screen.blit(person1, person1_rect)
    # screen.blit(person2, person2_rect)

    if train_rect.y > 850:
        train_rect.bottom = 10

    pygame.draw.line(roadFromTop, 'black', (0, 10), (50, 10), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 20), (50, 20), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 30), (50, 30), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 40), (50, 40), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 50), (50, 50), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 60), (50, 60), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 70), (50, 70), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 80), (50, 80), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 90), (50, 90), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 100), (50, 100), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 110), (50, 110), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 120), (50, 120), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 130), (50, 130), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 140), (50, 140), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 150), (50, 150), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 160), (50, 160), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 170), (50, 170), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 180), (50, 180), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 190), (50, 190), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 200), (50, 200), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 210), (50, 210), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 220), (50, 220), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 230), (50, 230), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 240), (50, 240), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 250), (50, 250), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 260), (50, 260), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 270), (50, 270), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 280), (50, 280), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 290), (50, 290), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 300), (50, 300), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 310), (50, 310), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 320), (50, 320), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 330), (50, 330), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 340), (50, 340), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 350), (50, 350), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 360), (50, 360), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 370), (50, 370), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 380), (50, 380), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 390), (50, 390), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 400), (50, 400), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 410), (50, 410), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 420), (50, 420), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 430), (50, 430), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 440), (50, 440), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 450), (50, 450), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 460), (50, 460), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 470), (50, 470), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 480), (50, 480), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 490), (50, 490), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 500), (50, 500), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 510), (50, 510), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 520), (50, 520), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 530), (50, 530), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 540), (50, 540), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 550), (50, 550), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 560), (50, 560), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 570), (50, 570), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 580), (50, 580), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 590), (50, 590), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 600), (50, 600), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 610), (50, 610), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 620), (50, 620), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 630), (50, 630), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 640), (50, 640), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 650), (50, 650), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 660), (50, 660), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 670), (50, 670), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 680), (50, 680), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 690), (50, 690), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 700), (50, 700), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 710), (50, 710), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 720), (50, 720), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 730), (50, 730), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 740), (50, 740), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 750), (50, 750), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 760), (50, 760), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 770), (50, 770), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 780), (50, 780), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 790), (50, 790), 2)
    pygame.draw.line(roadFromTop, 'black', (0, 800), (50, 800), 2)

    pygame.display.update()
    clock.tick(60)
